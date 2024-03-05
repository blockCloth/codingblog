package com.coding.blog.service.vo;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @User Administrator
 * @CreateTime 2023/12/26 13:25
 * @className com.coding.blog.service.vo.PostsQueryVo
 */
@Data
@ToString
public class PostsQueryVO {

    private Integer pageNum;
    private Integer pageSize;
    private String postsTitle;
    private String postStatus;
}