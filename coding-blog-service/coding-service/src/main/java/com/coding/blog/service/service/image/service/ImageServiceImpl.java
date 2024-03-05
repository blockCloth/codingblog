package com.coding.blog.service.service.image.service;

import cn.hutool.core.util.StrUtil;
import com.coding.blog.common.enumapi.StatusEnum;
import com.coding.blog.common.util.AsyncUtil;
import com.coding.blog.common.util.ExceptionUtil;
import com.coding.blog.common.util.MdImgLoader;
import com.coding.blog.service.service.image.oss.ImageUploader;
import com.github.hui.quick.plugin.base.constants.MediaType;
import com.github.hui.quick.plugin.base.file.FileReadUtil;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.coding.blog.service.service.image.oss.ImageUploader.STATIC_IMG_TYPE;

/**
 * @User Administrator
 * @CreateTime 2023/12/29 17:27
 * @className com.coding.blog.service.service.image.service.ImageServiceImpl
 */
@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageUploader imageUploader;


    @Value("${server.address}")
    private String serverAddress;

    @Value("${server.port}")
    private String serverPort;

    /**
     * 外网图片转存缓存
     */
    private LoadingCache<String, String> imgReplaceCache = CacheBuilder.newBuilder().maximumSize(300).expireAfterWrite(5, TimeUnit.MINUTES).build(new CacheLoader<String, String>() {
        @Override
        public String load(String img) {
            try {
                InputStream stream = FileReadUtil.getStreamByFileName(img);
                URI uri = URI.create(img);
                String path = uri.getPath();
                int index = path.lastIndexOf(".");
                String fileType = null;
                if (index > 0) {
                    // 从url中获取文件类型
                    fileType = path.substring(index + 1);
                }
                return imageUploader.upload(stream, fileType);
            } catch (Exception e) {
                log.error("外网图片转存异常! img:{}", img, e);
                return "";
            }
        }
    });

    @Override
    public String saveImg(HttpServletRequest request) {
        MultipartFile file = null;
        if (request instanceof MultipartHttpServletRequest) {
            file = ((MultipartHttpServletRequest) request).getFile("image");
        }
        if (file == null) {
            throw ExceptionUtil.of(StatusEnum.ILLEGAL_ARGUMENTS_MIXED, "缺少需要上传的图片");
        }

        // 目前只支持 jpg, png, webp 等静态图片格式
        String fileType = validateStaticImg(file.getContentType());
        if (fileType == null) {
            throw ExceptionUtil.of(StatusEnum.ILLEGAL_ARGUMENTS_MIXED, "图片只支持png,jpg,gif");
        }

        try {
            return imageUploader.upload(file.getInputStream(), fileType);
        } catch (IOException e) {
            log.error("Parse img from httpRequest to BufferedImage error! e:", e);
            throw ExceptionUtil.of(StatusEnum.UPLOAD_PIC_FAILED);
        }
    }

    @Override
    public String getFileName(String pathName) {
        if (StrUtil.isEmpty(pathName)) return null;
        File file = new File(pathName);

        return file.getName();
    }

    @Override
    public boolean deleteImage(String imageName) {
        return imageUploader.deleteImage(imageName);
    }

    @Override
    public String mdImgReplace(String content) {
        List<MdImgLoader.MdImg> imgList = MdImgLoader.loadImgs(content);
        if (CollectionUtils.isEmpty(imgList)) {
            return content;
        }

        if (imgList.size() == 1) {
            // 只有一张图片时，没有必要走异步，直接转存并返回
            MdImgLoader.MdImg img = imgList.get(0);
            String newImg = saveImg(img.getUrl());
            return StringUtils.replace(content, img.getOrigin(), "![" + img.getDesc() + "](" + newImg + ")");
        }

        // 超过1张图片时，做并发的图片转存，提升性能
        AsyncUtil.CompletableFutureBridge bridge = AsyncUtil.concurrentExecutor("MdImgReplace");
        Map<MdImgLoader.MdImg, String> imgReplaceMap = Maps.newHashMapWithExpectedSize(imgList.size());
        for (MdImgLoader.MdImg img : imgList) {
            bridge.runAsyncWithTimeRecord(() -> {
                imgReplaceMap.put(img, saveImg(img.getUrl()));
            }, img.getUrl());
        }
        bridge.allExecuted().prettyPrint();

        // 图片替换
        for (Map.Entry<MdImgLoader.MdImg, String> entry : imgReplaceMap.entrySet()) {
            MdImgLoader.MdImg img = entry.getKey();
            String newImg = entry.getValue();
            content = StringUtils.replace(content, img.getOrigin(), "![" + img.getDesc() + "](" + newImg + ")");
        }
        return content;
    }

    /**
     * 外网图片转存
     *
     * @param img
     * @return
     */
    @Override
    public String saveImg(String img) {
        if (imageUploader.uploadIgnore(img)) {
            // 已经转存过，不需要再次转存；非http图片，不处理
            //如果是本地图片，则上传至服务器
            if (isLocalImage(img)){
                return uploadLocalImage(img);
            }
            return img;
        }

        try {
            String ans = imgReplaceCache.get(img);
            if (StringUtils.isBlank(ans)) {
                return buildUploadFailImgUrl(img);
            }
            return ans;
        } catch (Exception e) {
            return buildUploadFailImgUrl(img);
        }
    }

    private boolean isLocalImage(String imagePath) {
        File file = new File(imagePath);
        return file.exists() && file.isFile();
    }

    @Override
    public String uploadLocalImage(String localImagePath) {
        File file = new File(localImagePath);
        if (!file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("The provided path is not a valid file: " + localImagePath);
        }

        String fileType = validateStaticImg(getFileExtension(file));

        if (fileType == null) {
            throw new IllegalArgumentException("Unsupported image type. The image must be png, jpg, or gif.");
        }

        try (InputStream inputStream = new FileInputStream(file)) {
            return imageUploader.upload(inputStream, fileType);
        } catch (IOException e) {
            log.error("Failed to read the image file: " + localImagePath, e);
            throw new RuntimeException("Failed to upload the image", e);
        }
    }

    private String getFileExtension(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return "image/" + fileName.substring(dotIndex + 1);
        }
        return null;
    }

    private String buildUploadFailImgUrl(String img) {
        return img.contains("saveError") ? img : img + "?&cause=saveError!";
    }

    /**
     * 图片格式校验
     * @param mime
     * @return
     */
    private String validateStaticImg(String mime) {
        if ("svg".equalsIgnoreCase(mime)) {
            // fixme 上传文件保存到服务器本地时，做好安全保护, 避免上传了要给攻击性的脚本
            return "svg";
        }

        if (mime.contains(MediaType.ImageJpg.getExt())) {
            mime = mime.replace("jpg", "jpeg");
        }
        for (MediaType type : STATIC_IMG_TYPE) {
            if (type.getMime().equals(mime)) {
                return type.getExt();
            }
        }
        return null;
    }
}
