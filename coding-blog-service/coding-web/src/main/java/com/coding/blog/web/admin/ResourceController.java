package com.coding.blog.web.admin;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.coding.blog.service.dto.ResourceParam;
import com.coding.blog.service.entity.Resource;
import com.coding.blog.service.service.IResourceService;
import com.coding.blog.service.vo.ResourceQueryVo;
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
 * 后台资源表 前端控制器
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
@Api(tags = "后台资源管理")
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private IResourceService resourceService;

    @ApiOperation("添加资源信息")
    @PostMapping("saveResource")
    public ResultObject saveResource(@Validated ResourceParam resourceParam){
        Resource resource = new Resource();
        BeanUtils.copyProperties(resourceParam,resource);
        if (resource != null && resourceService.saveResource(resource)){
            return ResultObject.success();
        }
        return ResultObject.failed();
    }

    @ApiOperation("修改资源信息")
    @PutMapping("updateResource")
    public ResultObject updateResource(@Validated ResourceParam resourceParam){
        Resource resource = new Resource();
        BeanUtils.copyProperties(resourceParam,resource);
        if (resource != null && resourceService.updateById(resource)){
            resourceService.delResourceCache();
            return ResultObject.success();
        }
        return ResultObject.failed();
    }

    @ApiOperation("删除资源信息")
    @DeleteMapping("deleteResource")
    public ResultObject deleteResource(Long resourceId){
        if (resourceId == null) return ResultObject.failed("资源ID不能为空！");

        if (resourceService.deleteResource(resourceId)){
            return ResultObject.success();
        }
        return ResultObject.failed();
    }

    @ApiOperation("分页查询资源信息")
    @GetMapping("queryListAll")
    public ResultObject queryListAll(ResourceQueryVo queryVo){
        IPage<Resource> resourceIPage = resourceService.queryListAll(queryVo);
        if (resourceIPage != null){
            return ResultObject.success(resourceIPage);
        }
        return ResultObject.failed();
    }

    @ApiOperation("查询详细资源信息")
    @GetMapping("queryDetail")
    public ResultObject queryDetail(Integer resourceId){
        Resource resource = resourceService.getById(resourceId);
        if (resource != null){
            return ResultObject.success(resource);
        }
        return ResultObject.failed();
    }

    @ApiOperation("获取所有资源信息")
    @GetMapping("listAll")
    public ResultObject listAll(){
        List<Resource> list = resourceService.list();
        if (CollUtil.isNotEmpty(list)){
            return ResultObject.success(list);
        }
        return ResultObject.failed("资源信息为空，请勿重试！");
    }
}
