package com.coding.blog.service.service.impl;

import com.coding.blog.common.enumapi.RedisConstants;
import com.coding.blog.common.util.RedisTemplateUtil;
import com.coding.blog.service.entity.Site;
import com.coding.blog.service.mapper.SiteMapper;
import com.coding.blog.service.service.ISiteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 站点配置 服务实现类
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
@Service
public class SiteServiceImpl extends ServiceImpl<SiteMapper, Site> implements ISiteService {

    @Autowired
    private RedisTemplateUtil redisTemplateUtil;

    @Override
    public boolean setBlogView() {
        Long incr = redisTemplateUtil.incr(RedisConstants.POST_BLOG_VIEW, 1L);
        return incr > 0;
    }

    @Override
    public Integer getBlogView() {
        Integer blogView = (Integer) redisTemplateUtil.get(RedisConstants.POST_BLOG_VIEW);
        return blogView == null ? 0 : blogView;
    }

}
