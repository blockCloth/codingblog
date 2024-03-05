package com.coding.blog.service.service.impl;

import com.coding.blog.service.entity.Comments;
import com.coding.blog.service.mapper.CommentsMapper;
import com.coding.blog.service.service.ICommentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements ICommentsService {

}
