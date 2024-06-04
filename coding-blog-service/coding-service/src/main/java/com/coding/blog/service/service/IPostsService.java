package com.coding.blog.service.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coding.blog.service.dto.PostsParam;
import com.coding.blog.service.entity.Posts;
import com.baomidou.mybatisplus.extension.service.IService;
import com.coding.blog.service.vo.FrontPostVo;
import com.coding.blog.service.vo.PostDetailVo;
import com.coding.blog.service.vo.PostsQueryVO;
import com.coding.blog.service.vo.YearPostVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 文章 服务类
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
public interface IPostsService extends IService<Posts> {

    boolean savePost(PostsParam postsParam);

    boolean updatePost(PostsParam postsParam);

    boolean deletePostById(Long postId);

    PostDetailVo queryPostDetailById(Long postId);

    boolean insertPostToTerm(Long postId, Long termTaxonomyId);

    boolean insertPostToTags(Long postId, List<Long> tagsId);

    boolean updatePostToTerm(Long postId, Long termTaxonomyId);

    boolean updatePostToTags(Long postId, List<Long> tagsId);

    boolean deletePostToTerm(Long postId);

    boolean deletePostToTags(Long postId);

    IPage<Posts> queryPostsList(PostsQueryVO postsQueryVO);

    int setArticleOnTop(Long postsId);

    int cancelArticleOnTop(Long postsId);

    String uploadMd(MultipartFile file);

    Page<FrontPostVo> getFrontIntroductionMsg(Integer pageNum, Integer pageSize);

    List<FrontPostVo> getPostsByTermId(Long termTaxonomyId);

    List<FrontPostVo> getPostsByTagId(Long postTagId);

    Page<YearPostVo> getPostByTime(Integer pageNum, Integer pageSize);

    Integer praisePost(String ipAddress, Long postId);

    Integer cancelPraise(String ipAddress, Long postId);

    List<Posts> recommendPosts(Long postsId);

    List<Posts> getPostsByContent(String content);

    List<Posts> getHotPosts();

    boolean setVisible(Long postId, Integer status);
}
