package com.coding.blog.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @User Administrator
 * @CreateTime 2023/12/29 17:17
 * @className com.coding.blog.common.properties.ImageProperties
 */
@Data
@Component
@ConfigurationProperties(prefix = "image")
public class ImageProperties {

    /**
     * 存储绝对路径
     */
    private String absTmpPath;

    /**
     * 存储相对路径
     */
    private String webImgPath;

    /**
     * 上传文件的临时存储目录
     */
    private String tmpUploadPath;

    /**
     * 访问图片的host
     */
    private String cdnHost;

    private OssProperties oss;

    public String buildImgUrl(String url) {
        if (!url.startsWith(cdnHost)) {
            return cdnHost + url;
        }
        return url;
    }
}
