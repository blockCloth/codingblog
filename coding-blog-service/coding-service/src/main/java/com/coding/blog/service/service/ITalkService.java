package com.coding.blog.service.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.coding.blog.service.dto.TalkParam;
import com.coding.blog.service.entity.Talk;
import com.coding.blog.service.vo.TalkVo;

import java.util.List;

/**
 * @User Administrator
 * @CreateTime 2024/3/24 10:07
 * @className com.coding.blog.service.service.ITalkService
 */
public interface ITalkService extends IService<Talk> {
    boolean setTalkStatus(Long talkId, Integer talkStatus);

    boolean setTalkOrder(Long talkId, Integer talkOrder);

    boolean saveTalk(TalkParam param);

    boolean updateTalk(TalkParam talkParam);

    boolean deleteTalkById(Long talkId);

    TalkVo getTalkById(Long talkId);

    List<TalkVo> listTalks();

    IPage<TalkVo> listFrontTalks(Integer current, Integer size);

    boolean talkLike(Long talkId);

    boolean cancelTalkLike(Long talkId);
}
