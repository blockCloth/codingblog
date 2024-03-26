package com.coding.blog.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coding.blog.service.entity.Talk;
import com.coding.blog.service.vo.TalkVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @User Administrator
 * @CreateTime 2024/3/24 10:08
 * @className com.coding.blog.service.mapper.TalkMapper
 */
public interface TalkMapper extends BaseMapper<Talk> {
    List<TalkVo> listTalks();

    TalkVo getTalkById(Long talkId);

    IPage<TalkVo> listFrontTalks(Page<TalkVo> page);
}
