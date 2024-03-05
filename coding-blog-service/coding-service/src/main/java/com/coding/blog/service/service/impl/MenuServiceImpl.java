package com.coding.blog.service.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.coding.blog.common.enumapi.RedisConstants;
import com.coding.blog.common.enumapi.StatusEnum;
import com.coding.blog.common.util.ExceptionUtil;
import com.coding.blog.common.util.RedisTemplateUtil;
import com.coding.blog.service.entity.Menu;
import com.coding.blog.service.entity.RoleMenuRelation;
import com.coding.blog.service.mapper.MenuMapper;
import com.coding.blog.service.mapper.RoleMenuRelationMapper;
import com.coding.blog.service.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coding.blog.service.vo.MenuDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 后台菜单表 服务实现类
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RoleMenuRelationMapper roleMenuRelationMapper;
    @Autowired
    private RedisTemplateUtil redisTemplateUtil;

    @Override
    public boolean saveMenu(Menu menu) {
        // 判断menu是否添加过
        if (menuMapper.selectCount(
                new QueryWrapper<Menu>().eq("title", menu.getTitle())) > 0) {
            ExceptionUtil.of(StatusEnum.SYSTEM_MENU_EXISTS);
        }

        if (menu.getParentId() == null || menu.getParentId() == 0) {
            menu.setParentId(0L);
            menu.setLevel(0);
        } else {
            // 查找父级目录的level
            Map<String, Object> map = new HashMap<>();
            map.put("menu_id", menu.getParentId());
            Menu parentMenu = menuMapper.selectByMap(map).get(0);
            menu.setParentId(parentMenu.getMenuId());
            menu.setLevel(parentMenu.getLevel() + 1);

        }
        // 设置创建时间
        menu.setCreateTime(LocalDateTime.now());
        menu.setSort(0);
        menu.setHidden(0);

        // 删除缓存
        delRedisCache();
        return save(menu);
    }

    @Override
    public boolean deleteMenuById(Long menuId) {
        // 判断菜单是否被引用
        if (roleMenuRelationMapper.selectCount(
                new QueryWrapper<RoleMenuRelation>().eq("menu_id", menuId)) > 0) {
            ExceptionUtil.of(StatusEnum.SYSTEM_DATA_USE);
        }
        // 判断该菜单下是否还有子菜单
        if (menuMapper.selectCount(
                new QueryWrapper<Menu>().eq("parent_id", menuId)) > 0) {
            ExceptionUtil.of(StatusEnum.SYSTEM_MENU_IS_CHILDREN);
        }
        //删除缓存
        delRedisCache();

        return menuMapper.deleteById(menuId) > 0;
    }

    @Override
    public List<MenuDetailVo> queryMenuById(Long menuId) {
        List<MenuDetailVo> menuDetailVos =
                (List<MenuDetailVo>) redisTemplateUtil.hGet(RedisConstants.REDIS_KEY_MENU_TREE,menuId.toString());

        if (CollUtil.isEmpty(menuDetailVos)) {
            // 查询指定顶级菜单及其所有子菜单
            List<MenuDetailVo> allMenus = menuMapper.queryMenuTreeById(menuId);

            // 使用Map来快速索引菜单项
            Map<Long, MenuDetailVo> menuMap = allMenus.stream()
                    .collect(Collectors.toMap(MenuDetailVo::getMenuId, Function.identity()));

            // 构建菜单树
            List<MenuDetailVo> menuTree = new ArrayList<>();

            for (MenuDetailVo menu : allMenus) {
                if (menu.getMenuId() == menuId) {
                    // 如果是顶级菜单，添加到最终的菜单树列表中
                    menuTree.add(menu);
                } else {
                    // 如果不是顶级菜单，找到其父菜单并添加到父菜单的子菜单列表中
                    MenuDetailVo parentMenu = menuMap.get(menu.getParentId());
                    if (parentMenu != null) {
                        parentMenu.getChildren().add(menu);
                    }
                }
            }
            redisTemplateUtil.hSet(RedisConstants.REDIS_KEY_MENU_TREE, menuId.toString(), menuTree);
            menuDetailVos = menuTree;
        }
        return menuDetailVos;
    }

    @Override
    public List<MenuDetailVo> queryAllMenus() {
        List<MenuDetailVo> menuDetailVos =
                (List<MenuDetailVo>) redisTemplateUtil.get(RedisConstants.REDIS_KEY_MENU);

        if (CollUtil.isEmpty(menuDetailVos)) {

            // 查询所有菜单项
            List<MenuDetailVo> allMenus = menuMapper.selectAllMenus();

            // 使用Map来快速索引菜单项
            Map<Long, MenuDetailVo> menuMap = allMenus.stream()
                    .collect(Collectors.toMap(MenuDetailVo::getMenuId, Function.identity()));

            // 构建菜单树
            List<MenuDetailVo> menuTree = new ArrayList<>();
            for (MenuDetailVo menu : allMenus) {
                if (menu.getParentId() == 0) {
                    // 如果是顶级菜单，添加到最终的菜单树列表中
                    menuTree.add(menu);
                } else {
                    // 如果不是顶级菜单，找到其父菜单并添加到父菜单的子菜单列表中
                    MenuDetailVo parentMenu = menuMap.get(menu.getParentId());
                    if (parentMenu != null) {
                        parentMenu.getChildren().add(menu);
                    }
                }
            }
            redisTemplateUtil.set(RedisConstants.REDIS_KEY_MENU, menuTree);
            menuDetailVos = menuTree;
        }
        return menuDetailVos;
    }

    @Override
    public void delRedisCache(){
        redisTemplateUtil.del(RedisConstants.REDIS_KEY_MENU_TREE);
        redisTemplateUtil.del(RedisConstants.REDIS_KEY_MENU);
    }
}
