package com.coding.blog.common.properties.sensitive;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @User Administrator
 * @CreateTime 2024/3/8 17:17
 * @className com.coding.blog.common.properties.sensitive.SensitiveProperty
 */
@Data
@Component
@ConfigurationProperties(prefix = "codingblog.sensitive")
public class SensitiveProperty {
    /**
     * true 表示开启敏感词校验
     */
    private Boolean enable;

    /**
     * 自定义的敏感词
     */
    private List<String> deny;

    /**
     * 自定义的非敏感词
     */
    private List<String> allow;
}
