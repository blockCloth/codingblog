package com.coding.blog.service;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.Types;
import java.util.Collections;

/**
 * @User Administrator
 * @CreateTime 2023/12/4 18:46
 * @className com.coding.blog.service.MybatisPlusGenerator
 */
public class MybatisPlusGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/coding_blog", "root", "123456")
                .globalConfig(builder -> {
                    builder.author("blockCloth") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(System.getProperty("user.dir") + "/coding-service/src/main/java"); // 指定输出目录
                })

                .packageConfig(builder -> {
                    builder.parent("com.coding.blog.service") // 设置父包名
                            .moduleName("") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, System.getProperty("user.dir") + "/coding-service/src/main/resources/mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder()
                            .enableLombok() // 开启 lombok 模式
                            .enableTableFieldAnnotation() // 开启字段注解
                            .enableRemoveIsPrefix() // 开启驼峰转连字符
                            .controllerBuilder().enableRestStyle(); // 开启生成@RestController 控制器
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }


}
