package com.coding.blog.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coding.blog.service.entity.TalkImage;
import com.coding.blog.service.mapper.TalkImageMapper;
import com.coding.blog.service.service.ITalkImageService;
import org.springframework.stereotype.Service;

/**
 * @User Administrator
 * @CreateTime 2024/3/24 11:14
 * @className com.coding.blog.service.service.impl.ITalkImageServiceImpl
 */
@Service
public class ITalkImageServiceImpl extends ServiceImpl<TalkImageMapper, TalkImage>
        implements ITalkImageService {
}
