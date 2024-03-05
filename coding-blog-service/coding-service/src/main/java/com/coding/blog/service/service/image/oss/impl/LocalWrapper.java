package com.coding.blog.service.service.image.oss.impl;

import cn.hutool.core.io.IORuntimeException;
import com.coding.blog.common.enumapi.StatusEnum;
import com.coding.blog.common.properties.ImageProperties;
import com.coding.blog.common.util.ExceptionUtil;
import com.coding.blog.service.service.image.oss.ImageUploader;
import com.github.hui.quick.plugin.base.file.FileWriteUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * @User Administrator
 * @CreateTime 2023/12/29 17:33
 * @className com.coding.blog.service.service.impl.image.ImageUploaderImpl
 */
@Slf4j
@Service
@ConditionalOnExpression(value = "#{'local'.equals(environment.getProperty('image.oss.type'))}")
public class LocalWrapper implements ImageUploader {

    @Autowired
    private ImageProperties imageProperties;
    private Random random;
    @Value("${server.address}")
    private String serverAddress;

    @Value("${server.port}")
    private String serverPort;

    public LocalWrapper() {
        random = new Random();
    }

    @Override
    public String upload(InputStream input, String fileType) {
        try {
            if (fileType == null) {
                // 根据魔数判断文件类型
                byte[] bytes = StreamUtils.copyToByteArray(input);
                input = new ByteArrayInputStream(bytes);
                fileType = getFileType((ByteArrayInputStream) input, fileType);
            }

            String path = imageProperties.getAbsTmpPath() + imageProperties.getWebImgPath();
            String fileName = genTmpFileName();

            FileWriteUtil.FileInfo file = FileWriteUtil.saveFileByStream(input, path, fileName, fileType);
            return buildServerImageName(imageProperties.buildImgUrl(imageProperties.getWebImgPath() + file.getFilename() + "." + file.getFileType()));
        } catch (Exception e) {
            log.error("Parse img from httpRequest to BufferedImage error! e:", e);
            throw ExceptionUtil.of(StatusEnum.UPLOAD_PIC_FAILED);
        }
    }

    private String buildServerImageName(String imagePath) {
        return "http://" + serverAddress + ":" + serverPort + imagePath;
    }

    @Override
    public boolean deleteImage(String imageName) {
        // String diskPath = imageProperties.getAbsTmpPath() + imageProperties.getWebImgPath() + imageName;
        // String uploadPath = imageProperties.getWebImgPath() + imageName;
        boolean isDelete = false;

        // 构造在系统盘上的文件的完整路径
        String diskPath = imageProperties.getAbsTmpPath() + imageProperties.getWebImgPath() + imageName;
        // 构造在服务器上的文件的完整路径
        // String serverPath = imageProperties.getWebImgPath() + imageName; // 请替换为你的服务器文件路径

        try {
            // 删除在系统盘上的文件
            Files.deleteIfExists(Paths.get(diskPath));
            // 删除在服务器上的文件
            // Files.deleteIfExists(Paths.get(serverPath));
            isDelete = true;
        } catch (IOException e) {
            // handle exception
            throw new IORuntimeException(e);
        }

        return isDelete;
    }

    /**
     * 外网图片转存判定，对于没有转存过的，且是http开头的网络图片时，才需要进行转存
     *
     * @param img
     * @return true 表示不需要转存
     */
    @Override
    public boolean uploadIgnore(String img) {
        if (StringUtils.isNotBlank(imageProperties.getCdnHost()) && img.startsWith(imageProperties.getCdnHost())) {
            return true;
        }

        // 如果是oss的图片，也不需要转存
        // if (StringUtils.isNotBlank(imageProperties.getOss().getHost()) && img.startsWith(imageProperties.getOss().getHost())) {
        //     return true;
        // }

        return !img.startsWith("http");
    }


    /**
     * 获取文件临时名称
     *
     * @return
     */
    private String genTmpFileName() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddhhmmssSSS")) + "_" + random.nextInt(100);
    }
}
