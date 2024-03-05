package com.coding.blog.service.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @User Administrator
 * @CreateTime 2024/1/21 21:54
 * @className com.coding.blog.service.vo.ResourceQueryVo
 */
@Data
@ToString
public class ResourceQueryVo {
    private Integer pageNum;
    private Integer pageSize;
    private Integer total;
    private Long categoryId;
    private String nameKeyword;
    private String urlKeyword;
}
