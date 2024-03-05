package com.coding.blog.service.service;

import com.coding.blog.service.entity.ResourceCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 资源分类表 服务类
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
public interface IResourceCategoryService extends IService<ResourceCategory> {

    List<ResourceCategory> getListAll();

    boolean saveResourceCategory(ResourceCategory resourceCategory);

    boolean deleteById(Long resourceCategoryId);

}
