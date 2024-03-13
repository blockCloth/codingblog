package com.coding.blog.service.service;

import com.coding.blog.service.entity.Message;

import java.util.List;
import java.util.Map;

/**
 * @User Administrator
 * @CreateTime 2024/3/10 15:07
 * @className com.coding.blog.service.service.MessageService
 */
public interface MessageService {
    boolean saveMessage(Message message);

    List<Message> list(Map<String, Object> messageParam);

    boolean updateMessage(Message message);

    boolean deleteMessage(Long id);

    boolean saveTag(String tagName);

    boolean deleteTag(String tagName);

    List<String> getTagList();

}
