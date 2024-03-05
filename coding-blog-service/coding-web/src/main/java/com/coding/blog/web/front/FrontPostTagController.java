package com.coding.blog.web.front;

import cn.hutool.core.collection.CollUtil;
import com.coding.blog.service.entity.PostTag;
import com.coding.blog.service.entity.TermTaxonomy;
import com.coding.blog.service.service.IPostTagService;
import com.coding.blog.service.service.ITermTaxonomyService;
import com.coding.blog.service.vo.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @User Administrator
 * @CreateTime 2024/1/31 20:23
 * @className com.coding.blog.web.front.FrontPostTagController
 */
@Api(tags = "前台标签信息")
@RestController
@RequestMapping("/front/tag")
public class FrontPostTagController {
    @Autowired
    private IPostTagService postTagService;

    @ApiOperation("获取所有标签")
    @GetMapping("getAllTerm")
    public ResultObject getAllTerm(){
        List<PostTag> list = postTagService.list();
        if (CollUtil.isNotEmpty(list)){
            return ResultObject.success(list);
        }
        return ResultObject.failed();
    }
}
