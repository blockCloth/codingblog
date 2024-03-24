package com.coding.blog.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coding.blog.service.entity.TalkImageRelation;
import com.coding.blog.service.mapper.TalkImageRelationMapper;
import com.coding.blog.service.service.ITalkImageRelationService;
import org.springframework.stereotype.Service;

/**
 * @User Administrator
 * @CreateTime 2024/3/24 11:14
 * @className com.coding.blog.service.service.impl.ITalkImageRelationServiceImpl
 */
@Service
public class ITalkImageRelationServiceImpl extends ServiceImpl<TalkImageRelationMapper, TalkImageRelation>
        implements ITalkImageRelationService {
}
