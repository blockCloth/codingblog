package com.coding.blog.service.mapper;

import com.coding.blog.service.entity.Menu;
import com.coding.blog.service.entity.RoleMenuRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 后台角色菜单关系表 Mapper 接口
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
public interface RoleMenuRelationMapper extends BaseMapper<RoleMenuRelation> {

    List<Long> selectMenuIdsByRoleId(Long roleId);

    List<Menu> listMenu(Long roleId);
}
