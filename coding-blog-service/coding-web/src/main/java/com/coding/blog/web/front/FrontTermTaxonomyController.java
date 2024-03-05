package com.coding.blog.web.front;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.coding.blog.service.entity.TermTaxonomy;
import com.coding.blog.service.service.ITermTaxonomyService;
import com.coding.blog.service.vo.FrontPostVo;
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
 * @CreateTime 2024/1/31 20:07
 * @className com.coding.blog.web.front.FrontTermTaxonomyController
 */
@Api(tags = "前台专栏信息")
@RestController
@RequestMapping("/front/term")
public class FrontTermTaxonomyController {
    @Autowired
    private ITermTaxonomyService taxonomyService;

    @ApiOperation("获取所有专栏")
    @GetMapping("getAllTerm")
    public ResultObject getAllTerm(){
        List<TermTaxonomy> list = taxonomyService.list(new QueryWrapper<TermTaxonomy>().ne("parent_id",0));
        if (CollUtil.isNotEmpty(list)){
            return ResultObject.success(list);
        }
        return ResultObject.failed();
    }


}
