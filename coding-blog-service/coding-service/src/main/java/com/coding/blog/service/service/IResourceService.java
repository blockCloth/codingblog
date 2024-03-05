package com.coding.blog.service.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.coding.blog.service.entity.Resource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.coding.blog.service.vo.ResourceQueryVo;

/**
 * <p>
 * 后台资源表 服务类
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
public interface IResourceService extends IService<Resource> {

    boolean saveResource(Resource resource);

    boolean deleteResource(Long resourceId);

    IPage<Resource> queryListAll(ResourceQueryVo queryVo);

    void delResourceCache();
}
