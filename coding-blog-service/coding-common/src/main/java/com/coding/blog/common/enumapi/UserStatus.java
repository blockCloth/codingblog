package com.coding.blog.common.enumapi;

/**
 * @User Administrator
 * @CreateTime 2023/12/6 15:05
 * @className com.coding.blog.common.enumapi.UserStatus
 */
public enum UserStatus {
    ENABLE(0),
    DISABLED(1);

    private Integer status;

    UserStatus() {
    }

    UserStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
