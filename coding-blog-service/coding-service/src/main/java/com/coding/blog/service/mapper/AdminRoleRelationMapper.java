package com.coding.blog.service.mapper;

import com.coding.blog.service.entity.AdminRoleRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coding.blog.service.entity.Resource;
import com.coding.blog.service.entity.Role;

import java.util.List;

/**
 * <p>
 * 后台用户和角色关系表 Mapper 接口
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
public interface AdminRoleRelationMapper extends BaseMapper<AdminRoleRelation> {

    List<Long> selectRoleIdsByUserId(Long userId);

    List<Role> queryRoles(Long userId);

    int roleRemove(Long userId, Long roleId);

    List<Role> getRoleByUserId(Long userId);

    List<Resource> getResourceList(Long usersId);
}
