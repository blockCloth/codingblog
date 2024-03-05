package com.coding.blog.service.service;

import com.coding.blog.service.entity.TermRelationships;
import com.baomidou.mybatisplus.extension.service.IService;
import com.coding.blog.service.entity.TermTaxonomy;

/**
 * <p>
 * 文章栏目关系表 服务类
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
public interface ITermRelationshipsService extends IService<TermRelationships> {
    boolean deleteTermRelationships(Long postsId);

    boolean insertOrUpdate(Long termTaxonomyId, Long postsId);

    TermTaxonomy queryTermTaxonomyById(Long postId);

}
