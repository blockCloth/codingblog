package com.coding.blog.service.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @User Administrator
 * @CreateTime 2024/1/24 22:55
 * @className com.coding.blog.service.vo.UserQueryVo
 */
@Data
@ToString
public class UserQueryVo {
    private Integer page;
    private Integer pageSize;
    private Integer total;
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 用户名
     */
    private String userLogin;
    /**
     * 昵称
     */
    private String userNiceName;
}
