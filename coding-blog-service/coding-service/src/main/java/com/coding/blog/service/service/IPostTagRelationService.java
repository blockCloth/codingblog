package com.coding.blog.service.service;

import com.coding.blog.service.entity.PostTag;
import com.coding.blog.service.entity.PostTagRelation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 标签文章关系表 服务类
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
public interface IPostTagRelationService extends IService<PostTagRelation> {

    boolean deletePostsTag(Long postId);
    boolean insertOrUpdate(List<Long> tagIds, Long postsId);
    List<PostTag> queryPostTagsById(Long postId);
}
