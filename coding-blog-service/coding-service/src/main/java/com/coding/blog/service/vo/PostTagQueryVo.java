package com.coding.blog.service.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @User Administrator
 * @CreateTime 2024/1/16 22:39
 * @className com.coding.blog.service.vo.PostTagQueryVo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostTagQueryVo {
    @NonNull
    private Integer page = 1;
    @NonNull
    private Integer pageSize = 10;
    @NonNull
    private Integer total = 0;
    @NonNull
    private String tagName = "";
}
