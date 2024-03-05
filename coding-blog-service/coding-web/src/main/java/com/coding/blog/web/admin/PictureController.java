package com.coding.blog.web.admin;

import com.coding.blog.service.dto.PictureParam;
import com.coding.blog.service.entity.Picture;
import com.coding.blog.service.service.IPictureService;
import com.coding.blog.service.vo.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @User Administrator
 * @CreateTime 2024/2/28 11:00
 * @className com.coding.blog.web.admin.PictureController
 */
@Api(tags = "背景图片管理")
@RestController
@RequestMapping("picture")
public class PictureController {
    @Autowired
    private IPictureService pictureService;

    @ApiOperation("添加背景图片")
    @PostMapping("save")
    public ResultObject saveBackgroundImg(@RequestBody PictureParam pictureParam){
        Picture picture = new Picture();
        BeanUtils.copyProperties(pictureParam,picture);
        //设置创建时间
        picture.setCreateTime(LocalDateTime.now());
        if (pictureService.save(picture)){
            return ResultObject.success();
        }
        return ResultObject.failed();
    }

    @ApiOperation("修改背景图片")
    @PutMapping("update")
    public ResultObject updateBackgroundImg(@RequestBody Picture picture){
        if (pictureService.updateById(picture)){
            return ResultObject.success();
        }
        return ResultObject.failed();
    }

    @ApiOperation("删除背景图片")
    @DeleteMapping("delete")
    public ResultObject deleteBackgroundImg(Long pictureId){
        if (pictureService.removeById(pictureId)){
            return ResultObject.success();
        }
        return ResultObject.failed();
    }

    @ApiOperation("获取所有背景图片")
    @GetMapping("getAll")
    public ResultObject getAllBackgroundImg(){
        List<Picture> list = pictureService.list();
        return ResultObject.success(list);
    }

    @ApiOperation("设置背景图片显示状态")
    @PutMapping("setVisible")
    public ResultObject setVisible(@RequestParam Long pictureId,
                                   @RequestParam Integer status){
        if(pictureService.setVisible(pictureId,status)){
            return ResultObject.success();
        }
        return ResultObject.failed();
    }
}
