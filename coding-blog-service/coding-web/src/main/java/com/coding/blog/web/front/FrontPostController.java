package com.coding.blog.web.front;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coding.blog.common.util.IPUtil;
import com.coding.blog.service.service.IPostsService;
import com.coding.blog.service.vo.FrontPostVo;
import com.coding.blog.service.vo.PostDetailVo;
import com.coding.blog.service.vo.ResultObject;
import com.coding.blog.service.vo.YearPostVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @User Administrator
 * @CreateTime 2024/1/31 15:49
 * @className com.coding.blog.web.front.FrontPostController
 */
@Api(tags = "前台文章信息")
@RestController
@RequestMapping("/front/post")
public class FrontPostController {

    @Autowired
    private IPostsService postsService;

    @ApiOperation("获取首页简介信息")
    @GetMapping("introduction")
    public ResultObject getFrontIntroductionMsg(@RequestParam Integer pageNum,
                                                @RequestParam Integer pageSize){
        Page<FrontPostVo> frontIntroductionMsg = postsService.getFrontIntroductionMsg(pageNum, pageSize);
        if (ObjectUtil.isNotEmpty(frontIntroductionMsg)){
            return ResultObject.success(frontIntroductionMsg);
        }
        return ResultObject.failed();
    }

    @ApiOperation("获取文章时间轴")
    @GetMapping("postsTime")
    public ResultObject getPostByTime(@RequestParam Integer pageNum,
                                      @RequestParam Integer pageSize){
        Page<YearPostVo> frontIntroductionMsg = postsService.getPostByTime(pageNum, pageSize);
        if (ObjectUtil.isNotEmpty(frontIntroductionMsg)){
            return ResultObject.success(frontIntroductionMsg);
        }
        return ResultObject.failed();
    }

    @ApiOperation("获取文章详细信息")
    @GetMapping("getDetailById")
    public ResultObject getDetailById(Long postId){
        PostDetailVo postDetailVo = postsService.queryPostDetailById(postId);
        if (postDetailVo != null){
            return ResultObject.success(postDetailVo);
        }
        return ResultObject.failed();
    }

    @ApiOperation("获取专栏下的所有文章")
    @GetMapping("getPostsByTermId")
    public ResultObject getPostsByTermId(Long termTaxonomyId){
        List<FrontPostVo> postVoList = postsService.getPostsByTermId(termTaxonomyId);
        if (CollUtil.isNotEmpty(postVoList))
            return ResultObject.success(postVoList);
        return ResultObject.failed();
    }

    @ApiOperation("获取标签下的所有文章")
    @GetMapping("getPostsByTagId")
    public ResultObject getPostsByTagId(Long postTagId){
        List<FrontPostVo> postVoList = postsService.getPostsByTagId(postTagId);
        if (CollUtil.isNotEmpty(postVoList))
            return ResultObject.success(postVoList);
        return ResultObject.failed();
    }

    @ApiOperation("文章点赞")
    @GetMapping("praise")
    public ResultObject praisePost(HttpServletRequest request,Long postId){
        String ipAddress = IPUtil.getClientIP(request);
        Integer praiseSize = postsService.praisePost(ipAddress, postId);

        if (praiseSize > 0){
            return ResultObject.success(praiseSize);
        }
        return ResultObject.failed();
    }

    @ApiOperation("取消文章点赞")
    @GetMapping("cancelPraise")
    public ResultObject cancelPraise(HttpServletRequest request,Long postId){
        String ipAddress = IPUtil.getClientIP(request);
        Integer praiseSize = postsService.cancelPraise(ipAddress, postId);

        if (praiseSize > 0){
            return ResultObject.success(praiseSize);
        }
        return ResultObject.failed();
    }

    @ApiOperation("推荐文章信息")
    @GetMapping("recommendPosts/{id}")
    public ResultObject recommendPosts(@PathVariable("id") Long postsId){
        return ResultObject.success(postsService.recommendPosts(postsId));
    }

    @ApiOperation("搜索文章信息")
    @GetMapping("getPostsByContent")
    public ResultObject getPostsByContent(String content){
        return ResultObject.success(postsService.getPostsByContent(content));
    }

    @ApiOperation("获取热门文章信息")
    @GetMapping("getHotPosts")
    public ResultObject getHotPosts(){
        return ResultObject.success(postsService.getHotPosts());
    }
}
