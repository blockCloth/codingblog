package com.coding.blog.web.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @User Administrator
 * @CreateTime 2023/12/6 10:37
 * @className com.coding.blog.web.security.config.IgnoreUrlsConfig
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "secure.ignored")
public class IgnoreUrlsConfig {
    private List<String> urls = new ArrayList<>();
}
