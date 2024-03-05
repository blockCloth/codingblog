package com.coding.blog.service.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.coding.blog.service.entity.Menu;
import com.coding.blog.service.entity.Resource;
import com.coding.blog.service.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.coding.blog.service.vo.RoleQueryVo;

import java.util.List;

/**
 * <p>
 * 后台用户角色表 服务类
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
public interface IRoleService extends IService<Role> {

    boolean addRole(Role role);

    boolean deleteRoleBatch(List<Integer> ids);

    IPage<Role> queryRolePages(RoleQueryVo roleQueryVo);

    boolean allocMenu(Long roleId, List<Long> menuIds);

    List<Menu> listMenus(Long roleId);

    boolean allocResources(Long roleId, List<Long> resIds);

    List<Resource> listResource(Long roleId);

    boolean deleteRoleById(Long roleId);
}
