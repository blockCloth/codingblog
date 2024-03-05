package com.coding.blog.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coding.blog.service.entity.Picture;
import com.coding.blog.service.mapper.PictureMapper;
import com.coding.blog.service.service.IPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @User Administrator
 * @CreateTime 2024/2/28 11:01
 * @className com.coding.blog.service.service.impl.IPictureServiceImpl
 */
@Service
public class IPictureServiceImpl extends ServiceImpl<PictureMapper, Picture> implements IPictureService {
    @Autowired
    private PictureMapper pictureMapper;

    @Override
    public boolean setVisible(Long pictureId, Integer status) {
        UpdateWrapper<Picture> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("is_visible",status);
        updateWrapper.eq("picture_id",pictureId);
        return pictureMapper.update(updateWrapper) > 0;
    }
}
