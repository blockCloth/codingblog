package com.coding.blog.web.admin;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.coding.blog.service.dto.PostTagParam;
import com.coding.blog.service.entity.PostTag;
import com.coding.blog.service.service.IPostTagService;
import com.coding.blog.service.vo.PostTagQueryVo;
import com.coding.blog.service.vo.PostTagVo;
import com.coding.blog.service.vo.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 标签表 前端控制器
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
@Api(tags = "后台标签管理")
@RestController
@RequestMapping("/postTag")
public class PostTagController {
    @Autowired
    private IPostTagService postTagService;

    @ApiOperation("添加标签")
    @PostMapping("save")
    public ResultObject savePostTag(@Validated PostTagParam postTagParam) {
        PostTag postTag = new PostTag();
        BeanUtils.copyProperties(postTagParam, postTag);
        if (postTag != null && postTagService.savePostTag(postTag)) {
            return ResultObject.success();
        }
        return ResultObject.failed();
    }

    @ApiOperation("修改标签")
    @PutMapping("update")
    public ResultObject updatePostTag(@Validated PostTagParam postTagParam) {
        PostTag postTag = new PostTag();
        BeanUtils.copyProperties(postTagParam, postTag);
        if (postTag != null && postTagService.updateById(postTag)) {
            postTagService.delPostTagCache();
            return ResultObject.success();
        }
        return ResultObject.failed();
    }

    @ApiOperation("删除标签")
    @DeleteMapping("delete")
    public ResultObject deletePostTag(@RequestParam Long postTagId) {
        if (postTagId == null) return ResultObject.failed("标签ID不能为空！");

        if (postTagService.deletePostTag(postTagId)) {
            return ResultObject.success();
        }
        return ResultObject.failed();
    }

    @ApiOperation("模糊查询所有标签")
    @GetMapping("queryAllPostTag")
    public ResultObject queryAllPostTag(PostTagQueryVo queryVo) {
        IPage<PostTag> postTags = postTagService.queryAllPostTag(queryVo);

        if (ObjectUtil.isNotEmpty(postTags)) {
            return ResultObject.success(postTags);
        }
        return ResultObject.failed();
    }

    @ApiOperation("查询所有标签")
    @GetMapping("listAll")
    public ResultObject listAll(PostTagQueryVo queryVo) {
        List<PostTag> list = postTagService.list();

        if (CollUtil.isNotEmpty(list)) {
            return ResultObject.success(list);
        }
        return ResultObject.failed();
    }

    @ApiOperation("查询标签对应的文章")
    @GetMapping("queryPostByTagId")
    public ResultObject queryPostByTagId(@RequestParam Long postTagId) {
        List<PostTagVo> postTags = postTagService.queryPostByTagId(postTagId);

        if (CollUtil.isNotEmpty(postTags)) {
            return ResultObject.success(postTags);
        }
        return ResultObject.failed("该标签没有对应的文章，请勿重试！");
    }
}
