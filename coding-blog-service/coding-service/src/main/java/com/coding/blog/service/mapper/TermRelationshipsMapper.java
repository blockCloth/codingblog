package com.coding.blog.service.mapper;

import com.coding.blog.service.entity.TermRelationships;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coding.blog.service.entity.TermTaxonomy;
import com.coding.blog.service.vo.TermTaxonomyPostVo;

import java.util.List;

/**
 * <p>
 * 文章栏目关系表 Mapper 接口
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
public interface TermRelationshipsMapper extends BaseMapper<TermRelationships> {

    List<TermTaxonomyPostVo> queryTermTaxonomyPosts(Long termTaxonomyId);

    TermTaxonomy queryTermTaxonomyById(Long postId);
}
