package com.coding.blog.service.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coding.blog.common.enumapi.RedisConstants;
import com.coding.blog.common.enumapi.StatusEnum;
import com.coding.blog.common.util.ExceptionUtil;
import com.coding.blog.common.util.RedisTemplateUtil;
import com.coding.blog.service.dto.PostsParam;
import com.coding.blog.service.entity.PostTag;
import com.coding.blog.service.entity.Posts;
import com.coding.blog.service.entity.TermTaxonomy;
import com.coding.blog.service.mapper.PostsMapper;
import com.coding.blog.service.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coding.blog.service.service.image.service.ImageService;
import com.coding.blog.service.vo.FrontPostVo;
import com.coding.blog.service.vo.PostDetailVo;
import com.coding.blog.service.vo.PostsQueryVO;
import com.coding.blog.service.vo.YearPostVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLDataException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static sun.font.TrueTypeFont.postTag;

/**
 * <p>
 * 文章 服务实现类
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
@Slf4j
@Service
public class PostsServiceImpl extends ServiceImpl<PostsMapper, Posts> implements IPostsService {
    @Autowired
    private PostsMapper postsMapper;
    @Autowired
    private IUsersService usersService;
    @Autowired
    private ITermRelationshipsService termRelationshipsService;
    @Autowired
    private IPostTagRelationService postTagRelationService;
    @Autowired
    private RedisTemplateUtil redisTemplateUtil;
    @Autowired
    private ImageService imageService;


    @Override
    @Transactional(rollbackFor = SQLDataException.class)
    public boolean savePost(PostsParam postsParam) {
        Posts posts = new Posts();
        BeanUtils.copyProperties(postsParam, posts);

        // TODO 设置文章属性
        // TODO 是否开启定时任务

        // 对文章图片进行转链
        posts.setPostContent(imageService.mdImgReplace(posts.getPostContent()));

        // 设置初始评论数
        posts.setCommentCount(0L);

        // 设置创建用户
        posts.setPostAuthor(usersService.getCurrentUserId());

        // 设置文章创建时间以及修改时间
        posts.setPostDate(LocalDateTime.now());
        posts.setPostModified(LocalDateTime.now());

        // 设置默认浏览量
        posts.setPageView(0L);

        // 保存文章
        save(posts);

        // 保存专栏信息
        insertPostTermRelation(postsParam, posts);

        // 保存标签信息
        insertOrUpdateTags(postsParam, posts);

        // 添加点赞量以及浏览量
        insertPostViewAndPraise(posts.getPostsId());

        return true;
    }

    private void insertPostViewAndPraise(Long postsId) {
        //设置随机浏览量(10~20)跟点赞量（5~10）
        Random rand = new Random();
        int view = rand.nextInt((20 - 10) + 1) + 10;
        redisTemplateUtil.hSet(RedisConstants.POST_PAGE_VIEW,postsId.toString(),(long)view);
        int praise = rand.nextInt((10 - 5) + 1) + 5;
        redisTemplateUtil.hSet(RedisConstants.POST_PRAISE,postsId.toString(),(long)praise);
    }


    @Override
    @Transactional(rollbackFor = SQLDataException.class)
    public boolean updatePost(PostsParam postsParam) {
        if (postsParam.getPostsId() == null) {
            log.warn("文章ID必须存在！");
            ExceptionUtil.of(StatusEnum.SYSTEM_POST_ID_NOT_EXISTS);
        }

        // 查询文章是否存在
        Posts selectPost = postsMapper.selectById(postsParam);
        if (selectPost == null) {
            log.error("文章不存在，文章ID{}", postsParam.getPostsId());
            ExceptionUtil.of(StatusEnum.SYSTEM_POST_NOT_EXISTS);
        }

        Posts posts = new Posts();
        BeanUtils.copyProperties(postsParam, posts);

        // TODO 设置文章属性
        // TODO 是否开启定时任务
        // 对文章图片进行转链
        posts.setPostContent(imageService.mdImgReplace(posts.getPostContent()));

        // 设置文章修改时间
        posts.setPostModified(LocalDateTime.now());

        // 保存文章
        updateById(posts);

        // 保存专栏信息
        insertPostTermRelation(postsParam, posts);

        // 保存标签信息
        insertOrUpdateTags(postsParam, posts);

        // 删除缓存信息
        delPostRedisCache(posts.getPostsId());
        return true;
    }

    @Override
    public boolean deletePostById(Long postId) {
        // 先删除标签属性
        postTagRelationService.deletePostsTag(postId);
        // 删除专栏信息
        termRelationshipsService.deleteTermRelationships(postId);


        boolean isDeleted = removeById(postId);
        if (isDeleted) {
            // 删除浏览量
            redisTemplateUtil.hDel(RedisConstants.POST_PAGE_VIEW, postId.toString());
            // 删除点赞信息
            redisTemplateUtil.hDel(RedisConstants.POST_PRAISE, postId.toString());
            // 删除缓存信息
            delPostRedisCache(postId);
        }
        return isDeleted;
    }

    /**
     * 获取文章详细信息
     * @param postId 文章ID
     * @return
     */
    @Override
    public PostDetailVo queryPostDetailById(Long postId) {
        PostDetailVo postDetailVo =
                (PostDetailVo) redisTemplateUtil.hGet(RedisConstants.REDIS_KEY_POST_SINGLE, postId.toString());

        if (postDetailVo == null) {
            PostDetailVo detailVo = new PostDetailVo();
            // 查询文章信息
            Posts posts = postsMapper.selectById(postId);
            if (posts == null) {
                ExceptionUtil.of(StatusEnum.SYSTEM_POST_NOT_EXISTS);
            }
            detailVo.setPosts(posts);
            // 查询作者名
            detailVo.setAuthorName(postsMapper.getAuthorName(postId));
            // 查询专栏信息
            TermTaxonomy termTaxonomy = termRelationshipsService.queryTermTaxonomyById(postId);
            if (termTaxonomy != null) {
                detailVo.setTermTaxonomy(termTaxonomy);
            }
            // 查询标签信息
            List<PostTag> postTags = postTagRelationService.queryPostTagsById(postId);
            if (CollUtil.isNotEmpty(postTags)) {
                detailVo.setPostTagList(postTags);
            }

            redisTemplateUtil.hSet(RedisConstants.REDIS_KEY_POST_SINGLE, postId.toString(), detailVo);
            postDetailVo = detailVo;
        }
        // 设置点赞数量
        postDetailVo.setPraiseSize(getPostPraiseSize(postId));
        // 增加浏览量
        redisTemplateUtil.hIncr(RedisConstants.POST_PAGE_VIEW, postId.toString(), 1L);
        postDetailVo.setViewSize(getPostViewSize(postId));

        return postDetailVo;
    }

    @Override
    public boolean insertPostToTerm(Long postId, Long termTaxonomyId) {
        return termRelationshipsService.insertOrUpdate(termTaxonomyId, postId);
    }

    @Override
    public boolean insertPostToTags(Long postId, List<Long> tagsId) {
        return postTagRelationService.insertOrUpdate(tagsId, postId);
    }

    @Override
    public boolean updatePostToTerm(Long postId, Long termTaxonomyId) {
        return termRelationshipsService.insertOrUpdate(termTaxonomyId, postId);
    }

    @Override
    public boolean updatePostToTags(Long postId, List<Long> tagsId) {
        return postTagRelationService.insertOrUpdate(tagsId, postId);
    }

    @Override
    public boolean deletePostToTerm(Long postId) {
        return termRelationshipsService.deleteTermRelationships(postId);
    }

    @Override
    public boolean deletePostToTags(Long postId) {
        return postTagRelationService.deletePostsTag(postId);
    }

    @Override
    public IPage<Posts> queryPostsList(PostsQueryVO postsQueryVO) {
        Page<Posts> page = new Page(postsQueryVO.getPageNum(), postsQueryVO.getPageSize());
        QueryWrapper<Posts> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("post_date");

        if (postsQueryVO.getPostsTitle() != null && postsQueryVO.getPostsTitle() != "")
            queryWrapper.like("post_title", postsQueryVO.getPostsTitle());

        if (postsQueryVO.getPostStatus() != null && postsQueryVO.getPostStatus() != "")
            queryWrapper.eq("post_status", postsQueryVO.getPostStatus());

        return postsMapper.selectPage(page, queryWrapper);
    }

    @Override
    public int setArticleOnTop(Long postsId) {
        return postsMapper.update(new UpdateWrapper<Posts>()
                .eq("posts_id", postsId)
                .set("menu_order", 1));
    }

    @Override
    public int cancelArticleOnTop(Long postsId) {
        return postsMapper.update(new UpdateWrapper<Posts>()
                .eq("posts_id", postsId)
                .set("menu_order", 0));
    }

    @Override
    public String uploadMd(MultipartFile file) {
        try {
            return IoUtil.read(file.getInputStream(), "UTF-8");
        } catch (IORuntimeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Page<FrontPostVo> getFrontIntroductionMsg(Integer pageNum, Integer pageSize) {
        Page<FrontPostVo> page = new Page<>(pageNum, pageSize);
        List<FrontPostVo> introductionMsg = postsMapper.getFrontIntroductionMsg();
        //设置分页对象的总页数
        page.setTotal(introductionMsg.size());
        introductionMsg = setPageRecordsSize(introductionMsg,pageNum,pageSize);

        //判断浏览量是否需要初始化
        if (!redisTemplateUtil.isExist(RedisConstants.POST_PAGE_VIEW))
            //初始化浏览量
            initHotPosts("view");
            //判断点赞量是否需要初始化
        if (!redisTemplateUtil.isExist(RedisConstants.POST_PRAISE))
            //初始化点赞量
            initHotPosts("praise");

        // 获取点赞数、浏览量
        for (FrontPostVo allPost : introductionMsg) {
            allPost.setPraiseSize(getPostPraiseSize(allPost.getPostsId()));
            allPost.setViewSize(getPostViewSize(allPost.getPostsId()));
        }

        for (FrontPostVo postVo : introductionMsg) {
            // 查询专栏名和标签名
            String termTaxonomyName = termRelationshipsService.queryTermTaxonomyById(postVo.getPostsId()).getName();
            postVo.setTermTaxonomyName(termTaxonomyName);
            List<String> tagNames = postTagRelationService.queryPostTagsById(postVo.getPostsId())
                    .stream()
                    .map(PostTag::getDescription)
                    .collect(Collectors.toList());
            postVo.setPostTagName(tagNames);
        }

        // 设置分页对象的总记录数和当前页数据
        page.setRecords(introductionMsg);
        return page;
    }

    private List<FrontPostVo> setPageRecordsSize(List<FrontPostVo> introductionMsg,Integer pageNum,Integer pageSize){
        // 计算实际数据的起始索引和结束索引
        int startIndex = (pageNum - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, introductionMsg.size());

        // 判断实际数据是否超出范围
        if (startIndex < introductionMsg.size()) {
            // 使用 subList 方法截取实际数据范围内的元素作为当前页的数据
            introductionMsg = introductionMsg.subList(startIndex, endIndex);
        } else {
            // 当起始索引超出实际数据范围时，返回空列表
            introductionMsg = Collections.emptyList();
        }

        return introductionMsg;
    }

    @Override
    public List<FrontPostVo> getPostsByTermId(Long termTaxonomyId) {
        return postsMapper.getPostsByTermId(termTaxonomyId);
    }

    @Override
    public List<FrontPostVo> getPostsByTagId(Long postTagId) {
        return postsMapper.getPostsByTagId(postTagId);
    }

    @Override
    public Page<YearPostVo> getPostByTime(Integer pageNum, Integer pageSize) {
        Page<YearPostVo> page = new Page<>(pageNum, pageSize);

        List<FrontPostVo> allPosts = postsMapper.getPostByTime();

        Map<Integer, List<FrontPostVo>> postsGroupedByYear = allPosts.stream()
                .collect(Collectors.groupingBy(FrontPostVo::getYear));

        List<YearPostVo> result = new ArrayList<>();
        for (Map.Entry<Integer, List<FrontPostVo>> entry : postsGroupedByYear.entrySet()) {
            YearPostVo yearlyPosts = new YearPostVo();
            yearlyPosts.setYear(entry.getKey());
            yearlyPosts.setPostVoList(entry.getValue());
            result.add(yearlyPosts);
        }
        result.sort((a, b) -> b.getYear().compareTo(a.getYear()));
        return page.setRecords(result);
    }

    @Override
    public Integer praisePost(String ipAddress, Long postId) {
        redisTemplateUtil.hIncr(RedisConstants.POST_PRAISE, postId.toString(), 1L);
        // 获取到增值
        return getPostPraiseSize(postId);
    }

    @Override
    public Integer cancelPraise(String ipAddress, Long postId) {
        redisTemplateUtil.hDecr(RedisConstants.POST_PRAISE, postId.toString(), 1L);
        // 获取到增值
        return getPostPraiseSize(postId);
    }

    @Override
    public List<Posts> recommendPosts(Long postsId) {
        return postsMapper.recommendPosts(postsId);
    }

    @Override
    public List<Posts> getPostsByContent(String content) {
        return postsMapper.getPostsByContent(content);
    }

    @Override
    public List<Posts> getHotPosts() {
        List<Posts> topFivePosts;
        //判断key是否存在
        if (!redisTemplateUtil.isExist(RedisConstants.POST_PAGE_VIEW)){
            initHotPosts("view");
        }
        Map<Object, Object> postView = redisTemplateUtil.hGetAll(RedisConstants.POST_PAGE_VIEW);
        topFivePosts = postView.entrySet().stream()
                .sorted((e1, e2) -> ((Integer)e2.getValue()).compareTo((Integer) e1.getValue()))
                .limit(5)
                .map(entry -> postsMapper.selectById(Long.parseLong((String) entry.getKey())))
                .filter(post -> post.getPostVisible() != 1)  // 过滤出visible不等于0的文章
                .collect(Collectors.toList());

        return topFivePosts;
    }

    @Override
    public boolean setVisible(Long postId, Integer status) {
        UpdateWrapper<Posts> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("posts_id",postId);
        updateWrapper.set("post_visible",status);
        return postsMapper.update(updateWrapper) > 0;
    }

    /**
     * 初始化所有文章的浏览量
     * @return
     */
    private void initHotPosts(String str){
        List<Posts> posts = postsMapper.selectList(null);
        Random rand = new Random();
        if (str.equals("view")){
            //此时需要初始化浏览量
            for (Posts post : posts) {
                // 检查文章的浏览量是否已经在Redis中
                Object pageView = redisTemplateUtil.hGet(RedisConstants.POST_PAGE_VIEW, post.getPostsId().toString());
                if (ObjectUtil.isEmpty(pageView)) {
                    // 如果没有，设置默认浏览量为10~20
                    int view = rand.nextInt((20 - 10) + 1) + 10;
                    redisTemplateUtil.hSet(RedisConstants.POST_PAGE_VIEW, post.getPostsId().toString(), (long)view);
                }
            }
        }else {
            //初始化点赞量
            for (Posts post : posts) {
                Object pagePraise = redisTemplateUtil.hGet(RedisConstants.POST_PRAISE, post.getPostsId().toString());
                // 检查文章的点赞量是否已经在Redis中
                if (ObjectUtil.isEmpty(pagePraise)) {
                    // 如果没有，设置默认点赞量为5~10
                    int praise = rand.nextInt((10 - 5) + 1) + 10;
                    redisTemplateUtil.hSet(RedisConstants.POST_PRAISE, post.getPostsId().toString(), (long)praise);
                }
            }
        }

    }

    /**
     * 获取点赞数量
     *
     * @param postId
     * @return
     */
    private Integer getPostPraiseSize(Long postId) {
        Integer praiseSize = (Integer) redisTemplateUtil.hGet(RedisConstants.POST_PRAISE, postId.toString());
        return praiseSize == null ? 0 : praiseSize;
    }

    /**
     * 获取浏览数量
     *
     * @param postId
     * @return
     */
    private Integer getPostViewSize(Long postId) {
        Integer postViewSize = (Integer) redisTemplateUtil.hGet(RedisConstants.POST_PAGE_VIEW, postId.toString());
        return postViewSize == null ? 0 : postViewSize;
    }

    /**
     * 保存或修改标签信息
     */
    private boolean insertOrUpdateTags(PostsParam postsParam, Posts posts) {
        log.info("准备文章{}标签{}信息", posts.getPostsId(), postsParam.getTags());
        // 判断IDS是否为空
        if (CollUtil.isEmpty(postsParam.getTags())) {
            return false;
        }
        // 准备修改标签信息
        return postTagRelationService.insertOrUpdate(postsParam.getTags(), posts.getPostsId());
    }

    /**
     * 保存文章专栏信息
     *
     * @return
     */
    private boolean insertPostTermRelation(PostsParam postsParam, Posts posts) {
        // 判断专栏ID是否为空
        if (postsParam.getTermTaxonomyId() == null) {
            return false;
        }
        return termRelationshipsService.insertOrUpdate(postsParam.getTermTaxonomyId(), posts.getPostsId());
    }


    private void delPostRedisCache(Long postId) {
        redisTemplateUtil.hDel(RedisConstants.REDIS_KEY_POST_SINGLE, postId.toString());
    }
}
