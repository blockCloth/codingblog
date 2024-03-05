package com.coding.blog.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.coding.blog.service.entity.PostTag;
import com.coding.blog.service.entity.PostTagRelation;
import com.coding.blog.service.mapper.PostTagRelationMapper;
import com.coding.blog.service.service.IPostTagRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLDataException;
import java.util.List;

/**
 * <p>
 * 标签文章关系表 服务实现类
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
@Slf4j
@Service
public class PostTagRelationServiceImpl extends ServiceImpl<PostTagRelationMapper, PostTagRelation> implements IPostTagRelationService {

    @Autowired
    private PostTagRelationMapper postTagRelationMapper;

    @Override
    public boolean deletePostsTag(Long postId) {
        log.info("准备删除旧的标签关联");

        return postTagRelationMapper.delete(
                new QueryWrapper<PostTagRelation>().eq("post_id",postId)) > 0;
    }

    @Override
    @Transactional(rollbackFor = SQLDataException.class)
    public boolean insertOrUpdate(List<Long> tagIds, Long postsId) {
        //先删除旧的标签
        deletePostsTag(postsId);

        //插入新的标签，并设置标签插入顺序
        int order = 0;
        for (Long tagId : tagIds) {
            //创建标签对象
            PostTagRelation postTagRelation = new PostTagRelation();
            postTagRelation.setPostId(postsId);
            postTagRelation.setPostTagId(tagId);
            postTagRelation.setTermOrder(order++);
            save(postTagRelation);
        }
        return order > 0;
    }

    @Override
    public List<PostTag> queryPostTagsById(Long postId) {

        return postTagRelationMapper.queryPostTagsById(postId);
    }
}
