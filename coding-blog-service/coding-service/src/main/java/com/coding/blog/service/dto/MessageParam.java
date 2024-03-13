package com.coding.blog.service.dto;

import com.coding.blog.service.entity.Message;
import lombok.Data;

import java.io.Serializable;

/**
 * @User Administrator
 * @CreateTime 2024/3/10 16:50
 * @className com.coding.blog.service.dto.MessageParam
 */
@Data
public class MessageParam implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer current = 1;
    private Integer size = 8;
    private String tag;
    private Message message;
}
