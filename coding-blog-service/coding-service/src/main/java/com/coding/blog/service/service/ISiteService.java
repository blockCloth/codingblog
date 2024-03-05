package com.coding.blog.service.service;

import com.coding.blog.service.entity.Site;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 站点配置 服务类
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
public interface ISiteService extends IService<Site> {

    boolean setBlogView();

    Integer getBlogView();
}
