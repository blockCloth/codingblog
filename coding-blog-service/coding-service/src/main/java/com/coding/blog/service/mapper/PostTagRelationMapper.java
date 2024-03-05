package com.coding.blog.service.mapper;

import com.coding.blog.service.entity.PostTag;
import com.coding.blog.service.entity.PostTagRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coding.blog.service.vo.PostTagVo;

import java.util.List;

/**
 * <p>
 * 标签文章关系表 Mapper 接口
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
public interface PostTagRelationMapper extends BaseMapper<PostTagRelation> {

    List<PostTagVo> queryPostByTagId(Long postTagId);

    List<PostTag> queryPostTagsById(Long postId);
}
