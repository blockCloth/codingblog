package com.coding.blog.web.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.coding.blog.service.entity.Picture;
import com.coding.blog.service.service.IPictureService;
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
 * @CreateTime 2024/2/28 12:33
 * @className com.coding.blog.web.front.FrontPictureController
 */
@Api(tags = "背景图片管理")
@RestController
@RequestMapping("/front")
public class FrontPictureController {
    @Autowired
    private IPictureService pictureService;

    @ApiOperation("获取网站背景图片")
    @GetMapping("getBackgroundImgList")
    public ResultObject getBackgroundImgList(){
        List<Picture> pictureList = pictureService.list(new QueryWrapper<Picture>().eq("is_visible", 0));

        return ResultObject.success(pictureList);
    }
}
