package com.coding.blog.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coding.blog.service.entity.Talk;
import com.coding.blog.service.vo.TalkVo;

import java.util.List;

/**
 * @User Administrator
 * @CreateTime 2024/3/24 10:08
 * @className com.coding.blog.service.mapper.TalkMapper
 */
public interface TalkMapper extends BaseMapper<Talk> {
    List<TalkVo> listTalks();


    TalkVo getTalkById(Long talkId);
}
