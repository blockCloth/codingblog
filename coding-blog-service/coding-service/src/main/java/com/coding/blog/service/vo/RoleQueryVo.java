package com.coding.blog.service.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @User Administrator
 * @CreateTime 2024/1/21 21:29
 * @className com.coding.blog.service.vo.RoleQueryVo
 */
@Data
@ToString
public class RoleQueryVo {
    private Integer pageNum;
    private Integer pageSize;
    private Integer total;
    private String roleName;

}
