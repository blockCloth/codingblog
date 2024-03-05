package com.coding.blog.service.mapper;

import com.coding.blog.service.entity.Menu;
import com.coding.blog.service.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 后台用户角色表 Mapper 接口
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
public interface RoleMapper extends BaseMapper<Role> {


    List<Menu> getListMensByUserId(Long usersId);

    List<Role> getListRoleByUserId(Long usersId);
}
