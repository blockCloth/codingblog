package com.coding.blog.service.mapper;

import com.coding.blog.service.entity.Resource;
import com.coding.blog.service.entity.RoleResourceRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 后台角色资源关系表 Mapper 接口
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
public interface RoleResourceRelationMapper extends BaseMapper<RoleResourceRelation> {

    List<Long> selectResIdsByRoleId(Long roleId);

    List<Resource> listResource(Long roleId);
}
