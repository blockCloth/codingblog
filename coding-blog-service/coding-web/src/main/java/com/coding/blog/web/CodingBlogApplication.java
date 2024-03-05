package com.coding.blog.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hello world!
 */
@Slf4j
@ComponentScan("com.coding.blog")
@ServletComponentScan
@SpringBootApplication
public class CodingBlogApplication {
    public static void main(String[] args) {

        SpringApplication.run(CodingBlogApplication.class, args);

    }
}
