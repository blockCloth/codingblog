package com.coding.blog.web.admin;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.coding.blog.service.dto.TermTaxonomyParam;
import com.coding.blog.service.entity.TermTaxonomy;
import com.coding.blog.service.service.ITermTaxonomyService;
import com.coding.blog.service.vo.ResultObject;
import com.coding.blog.service.vo.TermTaxonomyPostVo;
import com.coding.blog.service.vo.TermTaxonomyVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 栏目 前端控制器
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
@Api(tags = "后台专栏管理")
@RestController
@RequestMapping("/termTaxonomy")
public class TermTaxonomyController {
    @Autowired
    private ITermTaxonomyService taxonomyService;

    @ApiOperation("添加专栏信息")
    @PostMapping("save")
    public ResultObject saveTermTaxonomy(@Validated TermTaxonomyParam termTaxonomyParam){
        TermTaxonomy termTaxonomy = new TermTaxonomy();
        BeanUtils.copyProperties(termTaxonomyParam,termTaxonomy);
        if (termTaxonomy != null && taxonomyService.saveTermTaxonomy(termTaxonomy)){
            return ResultObject.success();
        }
        return ResultObject.failed();
    }

    @ApiOperation("修改专栏信息")
    @PutMapping("update")
    public ResultObject updateTermTaxonomy(@Validated TermTaxonomyParam termTaxonomyParam){
        TermTaxonomy termTaxonomy = new TermTaxonomy();
        BeanUtils.copyProperties(termTaxonomyParam,termTaxonomy);
        //设置修改时间
        termTaxonomy.setUpdateTime(LocalDateTime.now());
        if (termTaxonomy != null && taxonomyService.updateById(termTaxonomy)){
            //修改成功，删除缓存内容
            taxonomyService.delTermTaxonomyCache();
            return ResultObject.success();
        }
        return ResultObject.failed();
    }

    @ApiOperation("删除专栏信息")
    @DeleteMapping("delete")
    public ResultObject deleteTermTaxonomyById(@RequestParam Long termTaxonomyId){
        if (termTaxonomyId == null) return  ResultObject.failed("专栏ID不能为空！");

        //设置修改时间
        if (taxonomyService.deleteTermTaxonomyById(termTaxonomyId)){
            return ResultObject.success();
        }
        return ResultObject.failed();
    }

    @ApiOperation("查询专栏树")
    @GetMapping("queryTermTaxonomyTree")
    public ResultObject queryTermTaxonomyTree(@RequestParam Long termTaxonomyId){
        List<TermTaxonomyVo> termTaxonomies = taxonomyService.queryTermTaxonomyTree(termTaxonomyId);
        //设置修改时间
        if (CollUtil.isNotEmpty(termTaxonomies)){
            return ResultObject.success(termTaxonomies);
        }
        return ResultObject.failed();
    }

    @ApiOperation("查询所有专栏树")
    @GetMapping("queryAllTermTaxonomyTree")
    public ResultObject queryAllTermTaxonomyTree(){
        List<TermTaxonomyVo> termTaxonomies = taxonomyService.queryAllTermTaxonomyTree();
        //设置修改时间
        if (CollUtil.isNotEmpty(termTaxonomies)){
            return ResultObject.success(termTaxonomies);
        }
        return ResultObject.failed();
    }

    @ApiOperation("查询专栏下的文章列表")
    @GetMapping("queryTermTaxonomyPosts")
    public ResultObject queryTermTaxonomyPosts(@RequestParam Long termTaxonomyId){
        List<TermTaxonomyPostVo> termTaxonomyPosts = taxonomyService.queryTermTaxonomyPosts(termTaxonomyId);
        //设置修改时间
        if (CollUtil.isNotEmpty(termTaxonomyPosts)){
            return ResultObject.success(termTaxonomyPosts);
        }
        return ResultObject.failed();
    }

    @ApiOperation("查询所有父专栏")
    @GetMapping("queryTermTaxonmyTreeParent")
    public ResultObject queryTermTaxonomyTreeParent(){
        List<TermTaxonomy> termTaxonomies = taxonomyService.queryTermTaxonomyTreeParent();

        if (CollUtil.isNotEmpty(termTaxonomies)){
            return ResultObject.success(termTaxonomies);
        }
        return ResultObject.failed();
    }

    @ApiOperation("获取专栏详情")
    @GetMapping("getById")
    public ResultObject queryTermTaxonomyById(Long termTaxonomyId){
        TermTaxonomy termTaxonomy = taxonomyService.getById(termTaxonomyId);
        if (termTaxonomy != null){
            return ResultObject.success(termTaxonomy);
        }
        return ResultObject.failed();
    }
}
