package com.coding.blog.service.vo;

import com.coding.blog.service.entity.PostTag;
import com.coding.blog.service.entity.Posts;
import com.coding.blog.service.entity.TermTaxonomy;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @User Administrator
 * @CreateTime 2023/12/14 17:12
 * @className com.coding.blog.service.vo.PostDetailVo
 */
@Data
@ToString
public class PostDetailVo {
    /**
     * 文章信息
     */
    private Posts posts;
    /**
     * 专栏信息
     */
    private TermTaxonomy termTaxonomy;
    /**
     * 标签信息
     */
    private List<PostTag> postTagList;
    /**
     * 点赞数量
     */
    private Integer praiseSize;
    /**
     * 浏览数量
     */
    private Integer viewSize;
}
