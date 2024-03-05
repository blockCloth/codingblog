package com.coding.blog.service.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coding.blog.common.enumapi.RedisConstants;
import com.coding.blog.common.enumapi.StatusEnum;
import com.coding.blog.common.util.ExceptionUtil;
import com.coding.blog.common.util.RedisTemplateUtil;
import com.coding.blog.service.entity.Resource;
import com.coding.blog.service.entity.RoleResourceRelation;
import com.coding.blog.service.mapper.ResourceMapper;
import com.coding.blog.service.mapper.RoleResourceRelationMapper;
import com.coding.blog.service.service.IResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coding.blog.service.vo.ResourceQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 后台资源表 服务实现类
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {

    @Autowired
    private ResourceMapper resourceMapper;
    @Autowired
    private RoleResourceRelationMapper relationMapper;
    @Autowired
    private RedisTemplateUtil redisTemplateUtil;

    @Override
    public boolean saveResource(Resource resource) {
        // 查询该名称跟路径是否存在
        if (resourceMapper.selectCount(
                new QueryWrapper<Resource>().eq("name", resource.getName())
                        .or()
                        .eq("url", resource.getUrl())) > 0) {
            ExceptionUtil.of(StatusEnum.SYSTEM_RESOURCE_EXISTS);
        }
        resource.setCreateTime(LocalDateTime.now());

        // 删除缓存
        delResourceCache();
        return save(resource);
    }

    @Override
    public boolean deleteResource(Long resourceId) {
        if (relationMapper.selectCount(
                new QueryWrapper<RoleResourceRelation>().eq("resource_id", resourceId)) > 0) {
            ExceptionUtil.of(StatusEnum.SYSTEM_DATA_USE);
        }
        // 删除缓存
        delResourceCache();
        return resourceMapper.deleteById(resourceId) > 0;
    }

    @Override
    public IPage<Resource> queryListAll(ResourceQueryVo queryVo) {

        Page<Resource> page = new Page<>(queryVo.getPageNum(), queryVo.getPageSize());

        QueryWrapper<Resource> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotEmpty(queryVo.getCategoryId()))
            queryWrapper.eq("category_id", queryVo.getCategoryId());

        if (StrUtil.isNotEmpty(queryVo.getNameKeyword()))
            queryWrapper.like("name", queryVo.getNameKeyword());

        if (StrUtil.isNotEmpty(queryVo.getUrlKeyword()))
            queryWrapper.like("url", queryVo.getUrlKeyword());

        return resourceMapper.selectPage(page, queryWrapper);

    }

    @Override
    public void delResourceCache() {
        redisTemplateUtil.del(RedisConstants.REDIS_KEY_RESOURCE);
        redisTemplateUtil.del(RedisConstants.REDIS_KEY_RESOURCE_ADMIN);
    }
}
