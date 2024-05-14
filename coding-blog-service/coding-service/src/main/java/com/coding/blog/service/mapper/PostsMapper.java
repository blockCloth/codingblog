package com.coding.blog.service.mapper;

import com.coding.blog.service.entity.Posts;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coding.blog.service.vo.FrontPostVo;
import com.coding.blog.service.vo.PostDetailVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 文章 Mapper 接口
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
public interface PostsMapper extends BaseMapper<Posts> {

    List<FrontPostVo> getFrontIntroductionMsg();

    List<FrontPostVo> getPostsByTermId(Long termTaxonomyId);

    List<FrontPostVo> getPostsByTagId(@Param("postTagId") Long postTagId);

    List<FrontPostVo> getPostByTime();

    List<Posts> recommendPosts(Long postsId);

    List<Posts> getPostsByContent(String content);

    PostDetailVo queryPostDetailById(@Param("postId") Long postId);

    String getAuthorName(@Param("postId") Long postId);
}
