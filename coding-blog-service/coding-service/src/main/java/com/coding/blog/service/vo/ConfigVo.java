package com.coding.blog.service.vo;

import com.coding.blog.service.entity.Site;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @User Administrator
 * @CreateTime 2024/2/17 17:03
 * @className com.coding.blog.service.vo.ConfigVo
 */
@Data
@ToString
public class ConfigVo implements Serializable {
    /**
     * 站点信息
     */
    private Site site;

    /**
     * 文章数量
     */
    private Integer postSize;

    /**
     * 标签数量
     */
    private Integer postTagSize;

    /**
     * 专栏数量
     */
    private Integer termTaxonomySize;

    /**
     * 博客总浏览量
     */
    private Integer blogView;
}
