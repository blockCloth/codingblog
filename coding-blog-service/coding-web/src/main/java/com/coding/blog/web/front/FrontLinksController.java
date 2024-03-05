package com.coding.blog.web.front;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coding.blog.service.entity.Links;
import com.coding.blog.service.service.ILinksService;
import com.coding.blog.service.vo.FrontPostVo;
import com.coding.blog.service.vo.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @User Administrator
 * @CreateTime 2024/2/21 16:34
 * @className com.coding.blog.web.front.FrontLinksController
 */
@Api(tags = "前台友链信息")
@RestController
@RequestMapping("/front/link")
public class FrontLinksController {
    @Autowired
    private ILinksService linksService;

    @ApiOperation("获取友链信息")
    @GetMapping("linksList")
    public ResultObject linksList(){
        List<Links> links = linksService.list();
        return ResultObject.success(links);
    }
}
