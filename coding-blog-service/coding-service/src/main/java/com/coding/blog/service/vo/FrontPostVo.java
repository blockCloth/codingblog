package com.coding.blog.service.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @User Administrator
 * @CreateTime 2024/1/31 15:51
 * @className com.coding.blog.service.vo.FrontPostVo
 */
@Data
@ToString
public class FrontPostVo implements Serializable {

    /**
     * 文章ID
     */
    private Long postsId;
    /**
     * 文章标题
     */
    private String postTitle;
    /**
     * 专栏名称
     */
    private String termTaxonomyName;
    /**
     * 标签名称
     */
    private List<String> postTagName;
    /**
     * 点赞量
     */
    private Integer praiseSize;

    /**
     * 文章浏览量
     */
    private Integer viewSize;
    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime postDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime postModified;
    /**
     * 描述
     */
    private String postExcerpt;

    @JsonProperty("attribute")
    private String attribute;
    /**
     * 菜单排序
     */
    private Integer menuOrder;
    /**
     * 年份
     */
    private Integer year;
}