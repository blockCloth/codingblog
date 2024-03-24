package com.coding.blog.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLDataException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @User Administrator
 * @CreateTime 2024/3/24 10:08
 * @className com.coding.blog.service.service.impl.ITalkServiceImpl
 */
@Service
@Slf4j
public class ITalkServiceImpl extends ServiceImpl<TalkMapper,Talk> implements ITalkService{
    @Autowired
    private TalkMapper talkMapper;

    @Autowired
    private TalkImageMapper imageMapper;

    @Autowired
    private TalkImageRelationMapper relationMapper;

    @Override
    public boolean setTalkStatus(Long talkId, Integer talkStatus) {
        UpdateWrapper<Talk> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("talk_status",talkStatus);
        updateWrapper.eq("talk_id",talkId);

        return talkMapper.update(updateWrapper) > 0;
    }

    @Override
    public boolean setTalkOrder(Long talkId, Integer talkOrder) {
        UpdateWrapper<Talk> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("talk_order",talkOrder);
        updateWrapper.eq("talk_id",talkId);

        return talkMapper.update(updateWrapper) > 0;
    }

    @Override
    @Transactional(rollbackFor = SQLDataException.class)
    public boolean saveTalk(TalkParam param) {
        Talk talk = new Talk();
        BeanUtils.copyProperties(param,talk);
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

        return true;
    }

    @Override
    @Transactional(rollbackFor = SQLDataException.class)
    public boolean updateTalk(TalkParam param) {
        Talk talk = new Talk();
        BeanUtils.copyProperties(param,talk);

        talkMapper.updateById(talk);

        //修改图片之前，删除以前信息
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
        //先删除图片以及关联表内容
        removeImages(talkId);
        return removeById(talkId);
    }

    @Override
    public TalkVo getTalkById(Long talkId) {

        return talkMapper.getTalkById(talkId);
    }

    @Override
    public List<TalkVo> listTalks() {
        return talkMapper.listTalks();
    }

    private void removeImages(Long talkId){
        List<TalkImageRelation> talkRelations = relationMapper.selectList(new QueryWrapper<TalkImageRelation>().
                eq("talk_id", talkId));

        for (TalkImageRelation talkRelation : talkRelations) {
            imageMapper.deleteById(talkRelation.getTalkImageId());
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("talk_id",talkId);
        relationMapper.deleteByMap(map);
    }
}
