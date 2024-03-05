package com.coding.blog.service.service;

import com.coding.blog.service.entity.TermTaxonomy;
import com.baomidou.mybatisplus.extension.service.IService;
import com.coding.blog.service.vo.TermTaxonomyPostVo;
import com.coding.blog.service.vo.TermTaxonomyVo;

import java.util.List;

/**
 * <p>
 * 栏目 服务类
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
public interface ITermTaxonomyService extends IService<TermTaxonomy> {

    boolean saveTermTaxonomy(TermTaxonomy termTaxonomy);

    boolean deleteTermTaxonomyById(Long termTaxonomyId);

    List<TermTaxonomyVo> queryTermTaxonomyTree(Long termTaxonomyId);

    List<TermTaxonomyVo> queryAllTermTaxonomyTree();

    List<TermTaxonomyPostVo> queryTermTaxonomyPosts(Long termTaxonomyId);

    void delTermTaxonomyCache();

    List<TermTaxonomy> queryTermTaxonomyTreeParent();
}
