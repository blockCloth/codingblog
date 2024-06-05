package com.coding.blog.service.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coding.blog.common.enumapi.RedisConstants;
import com.coding.blog.common.enumapi.StatusEnum;
import com.coding.blog.common.util.ExceptionUtil;
import com.coding.blog.common.util.RedisTemplateUtil;
import com.coding.blog.service.entity.PostTag;
import com.coding.blog.service.entity.PostTagRelation;
import com.coding.blog.service.mapper.PostTagMapper;
import com.coding.blog.service.mapper.PostTagRelationMapper;
import com.coding.blog.service.service.IPostTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coding.blog.service.vo.PostTagQueryVo;
import com.coding.blog.service.vo.PostTagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
@Service
public class PostTagServiceImpl extends ServiceImpl<PostTagMapper, PostTag> implements IPostTagService {

    @Autowired
    private PostTagMapper postTagMapper;
    @Autowired
    private PostTagRelationMapper relationMapper;
    @Autowired
    private RedisTemplateUtil redisTemplateUtil;

    @Override
    public boolean savePostTag(PostTag postTag) {
        if (postTagMapper.selectCount(
                new QueryWrapper<PostTag>().eq("description", postTag.getDescription())) > 0) {
            ExceptionUtil.of(StatusEnum.SYSTEM_DATA_USE);
        }
        boolean isSaved = save(postTag);
        if (isSaved) {
            delPostTagCache();
        }
        return isSaved;
    }

    @Override
    public boolean deletePostTag(Long postTagId) {
        // 查看哪些地方引用了标签ID
        if (relationMapper.selectCount(
                new QueryWrapper<PostTagRelation>().eq("post_tag_id", postTagId)) > 0) {
            ExceptionUtil.of(StatusEnum.SYSTEM_DATA_USE);
        }
        delPostTagCache();
        return postTagMapper.deleteById(postTagId) > 0;
    }

    @Override
    public IPage<PostTag> queryAllPostTag(PostTagQueryVo queryVo) {
        Page<PostTag> page = new Page<>(queryVo.getPage(),queryVo.getPageSize());
        QueryWrapper<PostTag> queryWrapper = new QueryWrapper<>();

        if (StrUtil.isNotEmpty(queryVo.getTagName())){
            queryWrapper.like("description",queryVo.getTagName());
        }

        return postTagMapper.selectPage(page,queryWrapper);
    }

    @Override
    public List<PostTagVo> queryPostByTagId(Long postTagId) {
        List<PostTagVo> postTagVosCache =
                (List<PostTagVo>) redisTemplateUtil.hGet(RedisConstants.REDIS_KEY_POST_TAG_VO, postTagId.toString());
        if (CollUtil.isEmpty(postTagVosCache)) {
            List<PostTagVo> postTagVos = relationMapper.queryPostByTagId(postTagId);
            redisTemplateUtil.hSet(RedisConstants.REDIS_KEY_POST_TAG_VO, postTagId.toString(), postTagVos);
            postTagVosCache = postTagVos;
        }
        return postTagVosCache;
    }

    @Override
    public void delPostTagCache() {
        redisTemplateUtil.del(RedisConstants.REDIS_KEY_POST_TAG_VO);
        // redisTemplateUtil.del(RedisConstants.REDIS_KEY_POST_TAG);
    }
}
