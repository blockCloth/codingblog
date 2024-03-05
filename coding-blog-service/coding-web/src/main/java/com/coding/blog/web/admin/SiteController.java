package com.coding.blog.web.admin;

import com.coding.blog.service.dto.SiteParam;
import com.coding.blog.service.entity.Site;
import com.coding.blog.service.service.ISiteService;
import com.coding.blog.service.vo.ResultObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @User Administrator
 * @CreateTime 2024/1/25 21:40
 * @className com.coding.blog.web.admin.SiteController
 */
@Api(tags = "后台站点管理")
@RestController
@RequestMapping("/site")
public class SiteController {

    @Autowired
    private ISiteService siteService;
    private static ObjectMapper objectMapper = new ObjectMapper();

    @ApiOperation("获取站点配置信息")
    @GetMapping("getById")
    public ResultObject getById(){
        long count = siteService.count();
        if (count == 0) {
            return ResultObject.failed("没有配置信息");
        }
        List<Site> siteList = siteService.list();
        return ResultObject.success(siteList.get(0));
    }

    @ApiOperation("保存站点配置信息")
    @PostMapping("saveSiteInfo")
    public ResultObject saveSiteInfo(@RequestBody SiteParam siteParam) {
        List<Site> siteList = siteService.list();
        if (siteList.size() == 0) {
            return ResultObject.failed("请初始化站点数据");
        }
        Site site = siteService.getById(siteList.get(0).getSiteId());
        BeanUtils.copyProperties(siteParam, site);
        site.setUpdateTime(LocalDateTime.now());
        // handleAttribute(siteParam, site);
        return ResultObject.success(siteService.updateById(site) ? "更新成功" : "更新失败");
    }

    /**
     * 处理扩展字段
     */
    // private void handleAttribute(SiteParam siteParam, Site site) throws JsonProcessingException {
    //     if (StringUtils.isNotBlank(siteParam.getAttribute())) {
    //         Map attribute = objectMapper.readValue(siteParam.getAttribute(), Map.class);
    //         site.setAttribute(attribute);
    //     }
    // }
}
