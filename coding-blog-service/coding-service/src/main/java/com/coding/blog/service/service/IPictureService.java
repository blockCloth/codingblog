package com.coding.blog.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coding.blog.service.entity.Picture;

/**
 * @User Administrator
 * @CreateTime 2024/2/28 11:01
 * @className com.coding.blog.service.service.IPictureService
 */
public interface IPictureService extends IService<Picture> {
    boolean setVisible(Long pictureId, Integer status);
}
