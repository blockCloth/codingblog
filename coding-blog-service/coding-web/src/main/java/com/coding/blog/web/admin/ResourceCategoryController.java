package com.coding.blog.web.admin;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.coding.blog.service.dto.ResourceCategoryParam;
import com.coding.blog.service.entity.ResourceCategory;
import com.coding.blog.service.service.IResourceCategoryService;
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
 * 资源分类表 前端控制器
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
@Api(tags = "后台资源分类管理")
@RestController
@RequestMapping("/resourceCategory")
public class ResourceCategoryController {
    @Autowired
    private IResourceCategoryService resourceCategoryService;

    @ApiOperation("查询所有资源分类")
    @GetMapping("/listAll")
    public ResultObject listAll() {
        List<ResourceCategory> listAll = resourceCategoryService.getListAll();

        if (CollUtil.isNotEmpty(listAll)){
            return ResultObject.success(listAll);
        }
        return ResultObject.failed();
    }

    @ApiOperation("新增资源分类")
    @PostMapping("/save")
    public ResultObject save(@Validated ResourceCategoryParam resourceCategoryParam) {
        ResourceCategory resourceCategory = new ResourceCategory();
        BeanUtils.copyProperties(resourceCategoryParam,resourceCategory);

        if (resourceCategory != null && resourceCategoryService.saveResourceCategory(resourceCategory)){
            return ResultObject.success();
        }
        return ResultObject.failed();
    }

    @ApiOperation("修改资源分类")
    @PutMapping("/update")
    public ResultObject update(@Validated ResourceCategoryParam resourceCategoryParam) {
        ResourceCategory resourceCategory = new ResourceCategory();
        BeanUtils.copyProperties(resourceCategoryParam,resourceCategory);

        if (resourceCategory != null && resourceCategoryService.updateById(resourceCategory)){
            return ResultObject.success();
        }
        return ResultObject.failed();
    }

    @ApiOperation("删除资源分类")
    @DeleteMapping("/delete")
    public ResultObject delete(@RequestParam Long resourceCategoryId) {
        if (resourceCategoryId == null) return ResultObject.failed("资源分类ID不能为空！");

        if (resourceCategoryService.deleteById(resourceCategoryId)){
            return ResultObject.success();
        }
        return ResultObject.failed();
    }
}
