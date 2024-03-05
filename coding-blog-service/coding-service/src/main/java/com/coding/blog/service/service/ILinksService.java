package com.coding.blog.service.service;

import com.coding.blog.service.entity.Links;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 链接信息表 服务类
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
public interface ILinksService extends IService<Links> {

    boolean setVisibility(Long linkId, String hidden);

}
