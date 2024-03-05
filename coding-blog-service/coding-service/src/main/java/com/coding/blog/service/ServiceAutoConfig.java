package com.coding.blog.service;

import com.coding.blog.common.util.RedisTemplateUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @User Administrator
 * @CreateTime 2023/12/5 20:21
 * @className com.coding.blog.service.ServiceAutoConfig
 */
@Order(Integer.MIN_VALUE)
@Configuration
@ComponentScan("com.coding.blog.service")
@MapperScan(basePackages = "com.coding.blog.service.mapper")
public class ServiceAutoConfig {


}
