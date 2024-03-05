package com.coding.blog.service.vo;

import java.time.LocalDateTime;

/**
 * @User Administrator
 * @CreateTime 2023/12/13 8:59
 * @className com.coding.blog.service.vo.TermTaxonomyPostVo
 */
public class TermTaxonomyPostVo {
    /**
     * 文章名
     */
    private String postName;
    /**
     * 专栏名
     */
    private String termTaxonomyName;
    /**
     * 文章作者名
     */
    private String postUserName;
    /**
     * 文章创建时间
     */
    private LocalDateTime postCreateTime;
}
