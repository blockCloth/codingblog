package com.coding.blog.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.coding.blog.service.entity.Links;
import com.coding.blog.service.mapper.LinksMapper;
import com.coding.blog.service.service.ILinksService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 链接信息表 服务实现类
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
@Service
public class LinksServiceImpl extends ServiceImpl<LinksMapper, Links> implements ILinksService {

    @Autowired
    private LinksMapper linksMapper;

    @Override
    public boolean setVisibility(Long linkId, String hidden) {
        UpdateWrapper<Links> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("link_visible",hidden);
        updateWrapper.eq("link_id",linkId);
        return linksMapper.update(updateWrapper) > 0;
    }

}
