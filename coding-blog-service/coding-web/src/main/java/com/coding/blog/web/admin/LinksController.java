package com.coding.blog.web.admin;

import cn.hutool.core.util.ObjectUtil;
import com.coding.blog.service.dto.LinkParam;
import com.coding.blog.service.entity.Links;
import com.coding.blog.service.service.ILinksService;
import com.coding.blog.service.vo.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 链接信息表 前端控制器
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
@Api(tags = "友链管理器")
@RestController
@RequestMapping("/links")
public class LinksController {
    @Autowired
    private ILinksService linksService;

    @ApiOperation("添加友链")
    @PostMapping("save")
    public ResultObject saveLink(@RequestBody LinkParam param) {
        Links links = new Links();
        BeanUtils.copyProperties(param, links);
        if (linksService.save(links)) {
            return ResultObject.success("保存成功");
        }
        return ResultObject.failed();
    }

    @ApiOperation("修改友链")
    @PutMapping("update")
    public ResultObject updateLink(@RequestBody LinkParam param) {
        Links links = new Links();
        BeanUtils.copyProperties(param, links);
        if (linksService.updateById(links)) {
            return ResultObject.success("保存成功");
        }
        return ResultObject.failed();
    }

    @ApiOperation("删除友链")
    @DeleteMapping("delete")
    public ResultObject deleteLink(Long linkId) {
        if (linksService.removeById(linkId)) {
            return ResultObject.success("删除成功");
        }
        return ResultObject.failed();
    }

    @ApiOperation("获取友链详情")
    @GetMapping("getLinkById")
    public ResultObject getLinkById(Long linkId) {
        Links link = linksService.getById(linkId);
        if (link != null) {
            return ResultObject.success(link);
        }
        return ResultObject.failed();
    }

    @ApiOperation("获取所有友链")
    @GetMapping("getLinks")
    public ResultObject getLinks() {
        List<Links> links = linksService.list();
        if (links != null) {
            return ResultObject.success(links);
        }
        return ResultObject.failed();
    }

    @ApiOperation("设置友链可见状态")
    @PutMapping("setVisibility")
    public ResultObject setVisibility(@RequestParam Long linkId,@RequestParam String hidden) {
        if (linksService.setVisibility(linkId,hidden)) {
            return ResultObject.success("更新成功");
        }
        return ResultObject.failed();
    }

}
