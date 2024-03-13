package com.coding.blog.service.service.impl;

import com.coding.blog.common.enumapi.RedisConstants;
import com.coding.blog.common.util.RedisTemplateUtil;
import com.coding.blog.service.entity.Message;
import com.coding.blog.service.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @User Administrator
 * @CreateTime 2024/3/10 15:07
 * @className com.coding.blog.service.service.impl.MessageServiceImpl
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private RedisTemplateUtil redisTemplateUtil;

    @Override
    public boolean saveMessage(Message message) {
        if (message == null)
            return false;
        //判断是否第一次添加留言内容
        if (!redisTemplateUtil.isExist(RedisConstants.MESSAGE_BOARD)){
            message.setId(1L);
        }else {
            //查询ID列表
            message.setId(getMessageHighScore() + 1);
        }
        message.setLocalDateTime(LocalDateTime.now());
        redisTemplateUtil.zAdd(RedisConstants.MESSAGE_BOARD,message, message.getId());
        return true;
    }

    @Override
    public List<Message> list(Map<String, Object> map) {

        Set<Object> set = redisTemplateUtil.zRange(RedisConstants.MESSAGE_BOARD, 0, -1);
        List<Message> list = set.stream()
                .map(obj -> (Message) obj)
                .filter(message -> map.get("tag") == "" || map.get("tag").equals(message.getTag()))
                .filter(message -> message.getMessage().contains((CharSequence) map.get("message")))
                .sorted(Comparator.comparing(Message::getLocalDateTime,Comparator.reverseOrder()))
                .collect(Collectors.toList());
        list = getPage(list,(int)map.get("current"), (int)map.get("size"));


        return list;
    }

    @Override
    public boolean updateMessage(Message message) {
        if (message == null)
            return false;

        // 获取原始的Message
        Message originalMessage = getMessageById(message.getId());
        if (originalMessage == null)
            return false;

        // 删除原始的Message
        redisTemplateUtil.zRemove(RedisConstants.MESSAGE_BOARD, originalMessage);

        // 添加新的Message
        message.setLocalDateTime(LocalDateTime.now());
        redisTemplateUtil.zAdd(RedisConstants.MESSAGE_BOARD, message, message.getId());

        return true;
    }

    @Override
    public boolean deleteMessage(Long id) {
        Message originalMessage = getMessageById(id);
        return redisTemplateUtil.zRemove(RedisConstants.MESSAGE_BOARD, originalMessage) > 0;
    }

    @Override
    public boolean saveTag(String tagName) {
        redisTemplateUtil.sAdd(RedisConstants.MESSAGE_TAG,tagName);
        return true;
    }

    @Override
    public boolean deleteTag(String tagName) {
        return redisTemplateUtil.sRemove(RedisConstants.MESSAGE_TAG,tagName) > 0;
    }

    @Override
    public List<String> getTagList() {
        return redisTemplateUtil.sMembers(RedisConstants.MESSAGE_TAG)
                .stream()
                .map(o -> (String)o)
                .collect(Collectors.toList());
    }

    /**
     * 根据id获取旧留言信息
     * @param id
     * @return
     */
    private Message getMessageById(Long id) {
        Set<Object> set = redisTemplateUtil.zRange(RedisConstants.MESSAGE_BOARD, 0, -1);
        for (Object obj : set) {
            Message message = (Message) obj;
            if (message.getId().equals(id)) {
                return message;
            }
        }
        return null;
    }

    /**
     * 分割集合信息
     */
    public List<Message> getPage(List<Message> list, int page, int size) {
        int start = (page - 1) * size;
        int end = Math.min(start + size, list.size());
        return new ArrayList<>(list.subList(start, end));
    }

    /**
     * 获取留言ID
     * @return
     */
    private long getMessageHighScore(){
        Set<ZSetOperations.TypedTuple<Object>> set = redisTemplateUtil.zRangeWithScores(RedisConstants.MESSAGE_BOARD, -1, -1);
        if (set != null && !set.isEmpty()) {
            Double highestScore = set.iterator().next().getScore();
            return highestScore.longValue();
        }
        return 0L;
    }

}
