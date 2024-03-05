package com.coding.blog.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.coding.blog.common.enumapi.TermRelationType;
import com.coding.blog.service.entity.TermRelationships;
import com.coding.blog.service.entity.TermTaxonomy;
import com.coding.blog.service.mapper.TermRelationshipsMapper;
import com.coding.blog.service.service.ITermRelationshipsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLDataException;

/**
 * <p>
 * 文章栏目关系表 服务实现类
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
@Slf4j
@Service
public class TermRelationshipsServiceImpl extends ServiceImpl<TermRelationshipsMapper, TermRelationships> implements ITermRelationshipsService {

    @Autowired
    private TermRelationshipsMapper termRelationshipsMapper;

    @Override
    public boolean deleteTermRelationships(Long postsId) {
        log.info("删除栏目ID");
        return termRelationshipsMapper.delete(new QueryWrapper<TermRelationships>()
                .eq("term_relationships_id",postsId)) > 0;
    }

    @Override
    @Transactional(rollbackFor = SQLDataException.class)
    public boolean insertOrUpdate(Long termTaxonomyId, Long postsId) {
        //增加栏目ID前，先把栏目ID清空
        deleteTermRelationships(postsId);

        TermRelationships relationships = new TermRelationships();
        //设置文章ID
        relationships.setTermRelationshipsId(postsId);
        relationships.setTermTaxonomyId(termTaxonomyId);
        relationships.setTermOrder(0);
        relationships.setType(TermRelationType.CONTENT.getType());

        return save(relationships);
    }

    @Override
    public TermTaxonomy queryTermTaxonomyById(Long postId) {
        return termRelationshipsMapper.queryTermTaxonomyById(postId);
    }
}
