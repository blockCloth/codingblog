package com.coding.blog.service.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @User Administrator
 * @CreateTime 2024/3/10 12:38
 * @className com.coding.blog.service.entity.Message
 */
@Data
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 留言ID
     */
    private Long id;
    /**
     * 留言信息
     */
    private String message;
    /**
     * 字体颜色
     */
    private String color;
    /**
     * 字体大小
     */
    private Integer font_size;
    /**
     * 字体权重
     */
    private Integer font_weight;
    /**
     * 背景颜色
     */
    private String bg_color;
    /**
     * 背景图片
     */
    private String bg_url;
    /**
     * 用户ID
     */
    private Long user_id;
    /**
     * 标签名称
     */
    private String tag;
    /**
     * 用户昵称
     */
    private String nick_name;
    /**
     * 背景图片
     */
    private List<Object> bgList;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
