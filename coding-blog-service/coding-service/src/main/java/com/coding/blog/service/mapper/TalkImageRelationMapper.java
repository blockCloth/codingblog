package com.coding.blog.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coding.blog.service.entity.TalkImage;
import com.coding.blog.service.entity.TalkImageRelation;
import com.coding.blog.service.vo.TalkImageVo;

import java.util.List;

/**
 * @User Administrator
 * @CreateTime 2024/3/24 11:13
 * @className com.coding.blog.service.mapper.TalkImageRelationMapper
 */
public interface TalkImageRelationMapper extends BaseMapper<TalkImageRelation> {
    List<TalkImageVo> queryImages(Long talkId);
}
