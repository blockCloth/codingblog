package com.coding.blog.service.service.image.oss;

import com.github.hui.quick.plugin.base.constants.MediaType;
import com.github.hui.quick.plugin.base.file.FileReadUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @User Administrator
 * @CreateTime 2023/12/29 17:28
 * @className com.coding.blog.service.service.image.oss.ImageUploader
 */
public interface ImageUploader {
    String DEFAULT_FILE_TYPE = "txt";
    Set<MediaType> STATIC_IMG_TYPE = new HashSet<>(Arrays.asList(MediaType.ImagePng, MediaType.ImageJpg, MediaType.ImageWebp, MediaType.ImageGif));


    /**
     * 文件上传
     *
     * @param input
     * @param fileType
     * @return
     */
    String upload(InputStream input, String fileType);

    /**
     * 获取文件类型
     *
     * @param input
     * @param fileType
     * @return
     */
    default String getFileType(ByteArrayInputStream input, String fileType) {
        if (StringUtils.isNotBlank(fileType)) {
            return fileType;
        }

        MediaType type = MediaType.typeOfMagicNum(FileReadUtil.getMagicNum(input));
        if (STATIC_IMG_TYPE.contains(type)) {
            return type.getExt();
        }
        return DEFAULT_FILE_TYPE;
    }

    boolean deleteImage(String imageName);

    /**
     * 判断外网图片是否依然需要处理
     *
     * @param fileUrl
     * @return true 表示忽略，不需要转存
     */
    boolean uploadIgnore(String fileUrl);
}
