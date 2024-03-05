package com.coding.blog.common.enumapi;

/**
 * @User Administrator
 * @CreateTime 2023/12/14 8:58
 * @className com.coding.blog.common.enumapi.TermRelationType
 */
public enum TermRelationType {
    /**
     * 内容
     */
    CONTENT(1),
    /**
     * 内容链接
     */
    CONTENT_LINK(2),
    /**
     * 栏目链接
     */
    CHANNEL_LINK(3);


    private Integer type;

    public Integer getType() {
        return type;
    }

    TermRelationType(Integer type) {
        this.type = type;
    }
}
