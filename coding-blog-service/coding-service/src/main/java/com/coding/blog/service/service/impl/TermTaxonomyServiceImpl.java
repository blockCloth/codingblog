package com.coding.blog.service.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.coding.blog.common.enumapi.RedisConstants;
import com.coding.blog.common.enumapi.StatusEnum;
import com.coding.blog.common.util.ExceptionUtil;
import com.coding.blog.common.util.RedisTemplateUtil;
import com.coding.blog.service.entity.TermRelationships;
import com.coding.blog.service.entity.TermTaxonomy;
import com.coding.blog.service.entity.Users;
import com.coding.blog.service.mapper.TermRelationshipsMapper;
import com.coding.blog.service.mapper.TermTaxonomyMapper;
import com.coding.blog.service.model.AdminUserDetails;
import com.coding.blog.service.service.ITermTaxonomyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coding.blog.service.vo.TermTaxonomyPostVo;
import com.coding.blog.service.vo.TermTaxonomyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 栏目 服务实现类
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
@Service
public class TermTaxonomyServiceImpl extends ServiceImpl<TermTaxonomyMapper, TermTaxonomy> implements ITermTaxonomyService {

    @Autowired
    private TermTaxonomyMapper taxonomyMapper;
    @Autowired
    private TermRelationshipsMapper relationshipsMapper;
    @Autowired
    private RedisTemplateUtil redisTemplateUtil;

    @Override
    public boolean saveTermTaxonomy(TermTaxonomy termTaxonomy) {
        // 查询该专栏是否添加过
        if (taxonomyMapper.selectCount(
                new QueryWrapper<TermTaxonomy>().eq("name", termTaxonomy.getName())) > 0) {
            ExceptionUtil.of(StatusEnum.SYSTEM_DATA_USE);
        }
        // 判断是否首级专栏
        if (termTaxonomy.getParentId() == null) {
            termTaxonomy.setParentId(0L);
        }
        // 获取当前用户
        AdminUserDetails userDetails =
                (AdminUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users users = userDetails.getUsers();
        // 设置用户信息即创建时间
        termTaxonomy.setCreateUserId(users.getUsersId());
        termTaxonomy.setCreateTime(LocalDateTime.now());
        termTaxonomy.setUpdateTime(LocalDateTime.now());

        delTermTaxonomyCache();
        return save(termTaxonomy);
    }

    @Override
    public boolean deleteTermTaxonomyById(Long termTaxonomyId) {
        // 查询专栏是否被使用
        if (relationshipsMapper.selectCount(
                new QueryWrapper<TermRelationships>().eq("term_taxonomy_id", termTaxonomyId)) > 0) {
            ExceptionUtil.of(StatusEnum.SYSTEM_DATA_USE);
        }
        // 判断是否为还有子集专栏
        if (taxonomyMapper.selectCount(
                new QueryWrapper<TermTaxonomy>().eq("parent_id",termTaxonomyId)) > 0) {
            ExceptionUtil.of(StatusEnum.SYSTEM_TERM_IS_CHILDREN);
        }
        delTermTaxonomyCache();
        return removeById(termTaxonomyId);
    }

    @Override
    public List<TermTaxonomyVo> queryTermTaxonomyTree(Long termTaxonomyId) {
        List<TermTaxonomyVo> taxonomyVoCache =
                (List<TermTaxonomyVo>) redisTemplateUtil.hGet(RedisConstants.REDIS_KEY_TERMTAXONOMY_TREE,termTaxonomyId.toString());

        if (CollUtil.isEmpty(taxonomyVoCache)){
            List<TermTaxonomyVo> termTaxonomies = taxonomyMapper.queryTermTaxonomyTree(termTaxonomyId);

            Map<Long, TermTaxonomyVo> taxonomyVoMap = termTaxonomies.stream()
                    .collect(Collectors.toMap(TermTaxonomyVo::getTermTaxonomyId, Function.identity()));

            // 构建专栏树
            List<TermTaxonomyVo> termTaxonomyList = new ArrayList<>();

            for (TermTaxonomyVo termTaxonomy : termTaxonomies) {
                if (termTaxonomy.getTermTaxonomyId() == termTaxonomyId) {
                    termTaxonomyList.add(termTaxonomy);
                } else {
                    // 如果不是顶级专栏，找到其父专栏并将子专栏添加到其中
                    TermTaxonomyVo termTaxonomyVo = taxonomyVoMap.get(termTaxonomy.getParentId());
                    if (termTaxonomyVo != null) {
                        termTaxonomyVo.getChildren().add(termTaxonomy);
                    }
                }
            }

            redisTemplateUtil.hSet(RedisConstants.REDIS_KEY_TERMTAXONOMY,termTaxonomyId.toString(),termTaxonomyList);
            taxonomyVoCache = termTaxonomyList;
        }
        return taxonomyVoCache;
    }

    @Override
    public List<TermTaxonomyVo> queryAllTermTaxonomyTree() {
        List<TermTaxonomyVo> termTaxonomyVosCache =
                (List<TermTaxonomyVo>) redisTemplateUtil.get(RedisConstants.REDIS_KEY_TERMTAXONOMY);

        if (CollUtil.isEmpty(termTaxonomyVosCache)){
            List<TermTaxonomyVo> termTaxonomyVos = taxonomyMapper.queryAllTermTaxonomyTree();

            Map<Long, TermTaxonomyVo> taxonomyVoMap = termTaxonomyVos.stream()
                    .collect(Collectors.toMap(TermTaxonomyVo::getTermTaxonomyId, Function.identity()));

            // 构建菜单树
            List<TermTaxonomyVo> termTaxonomyList = new ArrayList<>();

            for (TermTaxonomyVo termTaxonomy : termTaxonomyVos) {
                if (termTaxonomy.getParentId() == 0) {
                    termTaxonomyList.add(termTaxonomy);
                } else {
                    TermTaxonomyVo termTaxonomyVo = taxonomyVoMap.get(termTaxonomy.getParentId());
                    if (termTaxonomyVo != null) {
                        termTaxonomyVo.getChildren().add(termTaxonomy);
                    }
                }
            }

            redisTemplateUtil.set(RedisConstants.REDIS_KEY_TERMTAXONOMY,termTaxonomyList);
            termTaxonomyVosCache = termTaxonomyList;
        }
        return termTaxonomyVosCache;
    }

    @Override
    public List<TermTaxonomyPostVo> queryTermTaxonomyPosts(Long termTaxonomyId) {
        return relationshipsMapper.queryTermTaxonomyPosts(termTaxonomyId);
    }

    @Override
    public void delTermTaxonomyCache(){
        redisTemplateUtil.del(RedisConstants.REDIS_KEY_TERMTAXONOMY_TREE);
        redisTemplateUtil.del(RedisConstants.REDIS_KEY_TERMTAXONOMY);
    }

    @Override
    public List<TermTaxonomy> queryTermTaxonomyTreeParent() {
        return taxonomyMapper.selectList(new QueryWrapper<TermTaxonomy>()
                .eq("parent_id",0));
    }
}

