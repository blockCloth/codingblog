package com.coding.blog.web.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.coding.blog.service.dto.PostsParam;
import com.coding.blog.service.entity.Posts;
import com.coding.blog.service.service.IPostsService;
import com.coding.blog.service.vo.PostDetailVo;
import com.coding.blog.service.vo.PostsQueryVO;
import com.coding.blog.service.vo.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文章 前端控制器
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
@Api(tags = "后台文章管理")
@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    private IPostsService postsService;

    @ApiOperation("保存文章信息")
    @PostMapping("save")
    public ResultObject savePost(@Validated PostsParam postsParam) {
        if (postsParam != null && postsService.savePost(postsParam)) {
            return ResultObject.success();
        }
        return ResultObject.failed();
    }

    @ApiOperation("修改文章信息")
    @PutMapping("update")
    public ResultObject updatePost(@Validated PostsParam postsParam) {
        if (postsParam != null && postsService.updatePost(postsParam)) {
            return ResultObject.success();
        }
        return ResultObject.failed();
    }

    @ApiOperation("删除文章信息")
    @DeleteMapping("delete")
    public ResultObject deletePostById(@RequestParam Long postId) {
        if (postId == null) return ResultObject.failed("文章ID不能为空！");

        if (postsService.deletePostById(postId)) {
            return ResultObject.success();
        }
        return ResultObject.failed();
    }

    @ApiOperation("查询文章详细信息")
    @GetMapping("queryPostDetailById")
    public ResultObject queryPostDetailById(@RequestParam Long postId) {
        if (postId == null) return ResultObject.failed("文章ID不能为空！");
        PostDetailVo postDetailVo = postsService.queryPostDetailById(postId);
        if (postDetailVo != null) {
            return ResultObject.success(postDetailVo);
        }
        return ResultObject.failed();
    }

    @ApiOperation("模糊查询文章列表")
    @GetMapping("queryPostsList")
    public ResultObject queryPostsList(PostsQueryVO postsQueryVO) {
        IPage<Posts> postsIPage = postsService.queryPostsList(postsQueryVO);

        if (postsIPage != null) {
            return ResultObject.success(postsIPage);
        }
        return ResultObject.failed();
    }

    @ApiOperation("设置文章置顶")
    @PostMapping("setArticleOnTop")
    public ResultObject setArticleOnTop(Long postsId) {
        if (postsService.setArticleOnTop(postsId) > 0) {
            return ResultObject.success();
        }
        return ResultObject.failed();
    }

    @ApiOperation("取消文章置顶")
    @PostMapping("cancelArticleOnTop")
    public ResultObject cancelArticleOnTop(Long postsId) {
        if (postsService.cancelArticleOnTop(postsId) > 0) {
            return ResultObject.success();
        }
        return ResultObject.failed();
    }

    @ApiOperation("绑定文章到专栏")
    @PostMapping("insertPostToTerm")
    public ResultObject insertPostToTerm(@RequestParam Long postId,
                                         @RequestParam Long termTaxonomyId) {

        if (postsService.insertPostToTerm(postId, termTaxonomyId)) {
            return ResultObject.success();
        }
        return ResultObject.failed();
    }

    @ApiOperation("绑定文章的标签信息")
    @PostMapping("insertPostToTags")
    public ResultObject insertPostToTags(@RequestParam Long postId,
                                         @RequestParam List<Long> tagsId) {

        if (postsService.insertPostToTags(postId, tagsId)) {
            return ResultObject.success();
        }
        return ResultObject.failed();
    }

    @ApiOperation("修改文章的专栏信息")
    @PutMapping("updatePostToTerm")
    public ResultObject updatePostToTerm(@RequestParam Long postId,
                                         @RequestParam Long termTaxonomyId) {
        if (postsService.updatePostToTerm(postId, termTaxonomyId)) {
            return ResultObject.success();
        }
        return ResultObject.failed();
    }

    @ApiOperation("修改文章的标签信息")
    @PutMapping("updatePostToTags")
    public ResultObject updatePostToTags(@RequestParam Long postId,
                                         @RequestParam List<Long> tagsId) {

        if (postsService.updatePostToTags(postId, tagsId)) {
            return ResultObject.success();
        }
        return ResultObject.failed();
    }

    @ApiOperation("删除文章的专栏信息")
    @DeleteMapping("deletePostToTerm")
    public ResultObject deletePostToTerm(@RequestParam Long postId) {

        if (postsService.deletePostToTerm(postId)) {
            return ResultObject.success();
        }
        return ResultObject.failed();
    }

    @ApiOperation("删除文章的标签信息")
    @DeleteMapping("deletePostToTags")
    public ResultObject deletePostToTags(@RequestParam Long postId) {

        if (postsService.deletePostToTags(postId)) {
            return ResultObject.success();
        }
        return ResultObject.failed();
    }

    @PostMapping("/uploadMd")
    @ApiOperation("解析MD文件")
    public ResultObject<Map<String, Object>> uploadMd(@RequestParam("file") MultipartFile file, HttpServletRequest req)  {
        Map<String, Object> map = new HashMap<>();
        String content = postsService.uploadMd(file);
        map.put("content", content);
        map.put("postTitle",file.getOriginalFilename());
        return ResultObject.success(map);
    }

    @ApiOperation("设置文章展示状态")
    @PutMapping("setVisible")
    public ResultObject setVisible(@RequestParam Long postId,
                                   @RequestParam Integer status){
        if(postsService.setVisible(postId,status)){
            return ResultObject.success();
        }
        return ResultObject.failed();
    }
}


