package com.coding.blog.service.mapper;

import com.coding.blog.service.entity.TermTaxonomy;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coding.blog.service.vo.TermTaxonomyVo;

import java.util.List;

/**
 * <p>
 * 栏目 Mapper 接口
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
public interface TermTaxonomyMapper extends BaseMapper<TermTaxonomy> {

    List<TermTaxonomyVo> queryTermTaxonomyTree(Long termTaxonomyId);

    List<TermTaxonomyVo> queryAllTermTaxonomyTree();
}
