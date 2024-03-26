package com.coding.blog.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coding.blog.common.enumapi.RedisConstants;
import com.coding.blog.common.util.RedisTemplateUtil;
import com.coding.blog.service.dto.TalkParam;
import com.coding.blog.service.entity.Talk;
import com.coding.blog.service.entity.TalkImage;
import com.coding.blog.service.entity.TalkImageRelation;
import com.coding.blog.service.mapper.TalkImageMapper;
import com.coding.blog.service.mapper.TalkImageRelationMapper;
import com.coding.blog.service.mapper.TalkMapper;
import com.coding.blog.service.service.ITalkService;
import com.coding.blog.service.vo.TalkVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLDataException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @User Administrator
 * @CreateTime 2024/3/24 10:08
 * @className com.coding.blog.service.service.impl.ITalkServiceImpl
 */
@Service
@Slf4j
public class ITalkServiceImpl extends ServiceImpl<TalkMapper, Talk> implements ITalkService {
    @Autowired
    private TalkMapper talkMapper;

    @Autowired
    private RedisTemplateUtil redisTemplateUtil;

    @Autowired
    private TalkImageMapper imageMapper;

    @Autowired
    private TalkImageRelationMapper relationMapper;

    @Override
    public boolean setTalkStatus(Long talkId, Integer talkStatus) {
        UpdateWrapper<Talk> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("talk_status", talkStatus);
        updateWrapper.eq("talk_id", talkId);

        return talkMapper.update(updateWrapper) > 0;
    }

    @Override
    public boolean setTalkOrder(Long talkId, Integer talkOrder) {
        UpdateWrapper<Talk> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("talk_order", talkOrder);
        updateWrapper.eq("talk_id", talkId);

        return talkMapper.update(updateWrapper) > 0;
    }

    @Override
    @Transactional(rollbackFor = SQLDataException.class)
    public boolean saveTalk(TalkParam param) {
        Talk talk = new Talk();
        BeanUtils.copyProperties(param, talk);
        talk.setCreateDate(LocalDateTime.now());

        talkMapper.insert(talk);

        for (String talkImage : param.getTalkImages()) {
            TalkImage image = new TalkImage();
            image.setTalkImage(talkImage);
            imageMapper.insert(image);

            TalkImageRelation relation = new TalkImageRelation();
            relation.setTalkId(talk.getTalkId());
            relation.setTalkImageId(image.getTalkImageId());
            relationMapper.insert(relation);
        }
        //初始点赞
        redisTemplateUtil.zAdd(RedisConstants.TALK_LIKE,talk.getTalkId(),0L);



        return true;
    }

    @Override
    @Transactional(rollbackFor = SQLDataException.class)
    public boolean updateTalk(TalkParam param) {
        Talk talk = new Talk();
        BeanUtils.copyProperties(param, talk);

        talkMapper.updateById(talk);

        // 修改图片之前，删除以前信息
        removeImages(talk.getTalkId());

        for (String talkImage : param.getTalkImages()) {
            TalkImage image = new TalkImage();
            image.setTalkImage(talkImage);
            imageMapper.insert(image);

            TalkImageRelation relation = new TalkImageRelation();
            relation.setTalkId(talk.getTalkId());
            relation.setTalkImageId(image.getTalkImageId());
            relationMapper.insert(relation);
        }

        return true;
    }

    @Override
    public boolean deleteTalkById(Long talkId) {
        // 先删除图片以及关联表内容
        removeImages(talkId);
        return removeById(talkId);
    }

    @Override
    public TalkVo getTalkById(Long talkId) {
        TalkVo talkById = talkMapper.getTalkById(talkId);
        talkById.setTalkImages(relationMapper.queryImages(talkId));
        return talkById;
    }

    @Override
    public List<TalkVo> listTalks() {
        List<TalkVo> talkVoList = talkMapper.listTalks();
        for (TalkVo talkVo : talkVoList) {
            talkVo.setTalkImages(relationMapper.queryImages(talkVo.getTalkId()));
        }
        return talkVoList;
    }

    @Override
    public IPage<TalkVo> listFrontTalks(Integer current, Integer size) {
        // int offset = (current - 1) * size;
        // RowBounds rowBounds = new RowBounds(offset, size);
        //
        // List<TalkVo> list = talkMapper.listFrontTalks(rowBounds);
        //
        // for (TalkVo talkVo : list) {
        //     talkVo.setTalkImages(relationMapper.queryImages(talkVo.getTalkId()));
        // }
        //
        // return list;
        Page<TalkVo> page = new Page<>(current, size);
        IPage<TalkVo> talkVoIPage = talkMapper.listFrontTalks(page);
        for (TalkVo record : talkVoIPage.getRecords()) {
            record.setTalkImages(relationMapper.queryImages(record.getTalkId()));
            record.setTalkLike(getTalkLike(record.getTalkId()));
        }
        return talkVoIPage;

    }

    @Override
    public boolean talkLike(Long talkId) {
        if (!redisTemplateUtil.isExist(RedisConstants.TALK_LIKE)){
            redisTemplateUtil.zAdd(RedisConstants.TALK_LIKE,talkId,0L);
        }
        Double score = redisTemplateUtil.zScore(RedisConstants.TALK_LIKE, talkId);
        redisTemplateUtil.zIncrBy(RedisConstants.TALK_LIKE,talkId,score + 1);

        return true;
    }

    @Override
    public boolean cancelTalkLike(Long talkId) {
        Double score = redisTemplateUtil.zScore(RedisConstants.TALK_LIKE, talkId);
        redisTemplateUtil.zIncrBy(RedisConstants.TALK_LIKE,talkId,score - 1);
        return true;
    }

    private Integer getTalkLike(Long talkId){
        if (!redisTemplateUtil.isExist(RedisConstants.TALK_LIKE))
            initTalkLike();

        Integer likeNum = redisTemplateUtil.zScore(RedisConstants.TALK_LIKE, talkId).intValue();
        return likeNum == null ? 0 : likeNum;
    }

    private void initTalkLike(){
        for (Talk talk : list()) {
            redisTemplateUtil.zAdd(RedisConstants.TALK_LIKE,talk.getTalkId(),0L);
        }
    }
    private void removeImages(Long talkId) {
        List<TalkImageRelation> talkRelations = relationMapper.selectList(new QueryWrapper<TalkImageRelation>().
                eq("talk_id", talkId));

        for (TalkImageRelation talkRelation : talkRelations) {
            imageMapper.deleteById(talkRelation.getTalkImageId());
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("talk_id", talkId);
        relationMapper.deleteByMap(map);
    }
}
