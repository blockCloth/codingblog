package com.coding.blog.service.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coding.blog.common.enumapi.RedisConstants;
import com.coding.blog.common.enumapi.StatusEnum;
import com.coding.blog.common.util.ExceptionUtil;
import com.coding.blog.common.util.RedisTemplateUtil;
import com.coding.blog.service.entity.*;
import com.coding.blog.service.mapper.*;
import com.coding.blog.service.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coding.blog.service.vo.RoleQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLDataException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 后台用户角色表 服务实现类
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private ResourceMapper resourceMapper;
    @Autowired
    private AdminRoleRelationMapper adminRoleRelationMapper;
    @Autowired
    private RoleMenuRelationMapper roleMenuRelationMapper;
    @Autowired
    private RoleResourceRelationMapper resourceRelationMapper;
    @Autowired
    private RedisTemplateUtil redisTemplateUtil;

    @Override
    public boolean addRole(Role role) {
        // 查询此角色是否存在
        if (roleMapper.selectCount(
                new QueryWrapper<Role>().eq("name", role.getName())) > 0) {
            ExceptionUtil.of(StatusEnum.SYSTEM_ROLE_EXISTS);
        }
        // 设置创建时间以及角色状态
        role.setCreateTime(LocalDateTime.now());
        role.setStatus(1); // 0表示禁用，1表示启用
        return save(role);
    }

    @Override
    @Transactional(rollbackFor = SQLDataException.class)
    public boolean deleteRoleBatch(List<Integer> ids) {

        // 没有判断角色是否被使用
        return roleMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public IPage<Role> queryRolePages(RoleQueryVo roleQueryVo) {
        Page<Role> page = new Page<>(roleQueryVo.getPageNum(), roleQueryVo.getPageSize());

        QueryWrapper<Role> queryWrapper = new QueryWrapper<Role>();
        if (StrUtil.isNotEmpty(roleQueryVo.getRoleName())){
            queryWrapper.like("name",roleQueryVo.getRoleName());
        }
        return roleMapper.selectPage(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = SQLDataException.class)
    public boolean allocMenu(Long roleId, List<Long> menuIds) {
        menuIds.removeIf(Objects::isNull);
        // 判断角色是否存在
        if (roleMapper.selectById(roleId) == null) {
            // 此角色不存在
            ExceptionUtil.of(StatusEnum.SYSTEM_ROLE_NOT_EXISTS);
        }
        //添加之前删除所有菜单，在添加新菜单
        roleMenuRelationMapper.delete(new QueryWrapper<RoleMenuRelation>().eq("role_id",roleId));
        // 添加菜单
        for (Long menuId : menuIds) {
            RoleMenuRelation roleMenuRelation = new RoleMenuRelation();
            roleMenuRelation.setRoleId(roleId);
            roleMenuRelation.setMenuId(menuId);
            roleMenuRelationMapper.insert(roleMenuRelation);
        }
        return true;
    }

    @Override
    public List<Menu> listMenus(Long roleId) {

        return roleMenuRelationMapper.listMenu(roleId);
    }

    @Override
    public boolean allocResources(Long roleId, List<Long> resIds) {
        resIds.removeIf(Objects::isNull);
        // 判断角色是否存在
        if (roleMapper.selectById(roleId) == null) {
            // 此角色不存在
            ExceptionUtil.of(StatusEnum.SYSTEM_ROLE_NOT_EXISTS);
        }
        //删除之前所有资源，再重新进行添加
        resourceRelationMapper.delete(new QueryWrapper<RoleResourceRelation>().eq("role_id",roleId));

        // 添加资源菜单
        for (Long resId : resIds) {
            RoleResourceRelation roleResourceRelation = new RoleResourceRelation();
            roleResourceRelation.setRoleId(roleId);
            roleResourceRelation.setResourceId(resId);
            resourceRelationMapper.insert(roleResourceRelation);
        }
        //删除Redis缓存
        redisTemplateUtil.del(RedisConstants.REDIS_KEY_RESOURCE_ADMIN);
        return true;
    }

    @Override
    public List<Resource> listResource(Long roleId) {

        return resourceRelationMapper.listResource(roleId);
    }

    @Override
    @Transactional
    public boolean deleteRoleById(Long roleId) {
        QueryWrapper<AdminRoleRelation> adminRole = new QueryWrapper<AdminRoleRelation>().eq("role_id", roleId);
        QueryWrapper<RoleMenuRelation> roleMenu = new QueryWrapper<RoleMenuRelation>().eq("role_id", roleId);
        QueryWrapper<RoleResourceRelation> roleResource = new QueryWrapper<RoleResourceRelation>().eq("role_id", roleId);
        // 判断角色是否被引用
        if (adminRoleRelationMapper.selectCount(adminRole) > 0) {
            ExceptionUtil.of(StatusEnum.SYSTEM_DATA_USE);
        }
        //若角色未跟用户关联，则删除菜单以及资源
        roleMenuRelationMapper.delete(roleMenu);
        resourceRelationMapper.delete(roleResource);
        return removeById(roleId);
    }
}
