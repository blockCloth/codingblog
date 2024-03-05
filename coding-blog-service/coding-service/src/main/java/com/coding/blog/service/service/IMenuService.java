package com.coding.blog.service.service;

import com.coding.blog.service.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.coding.blog.service.vo.MenuDetailVo;

import java.util.List;

/**
 * <p>
 * 后台菜单表 服务类
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
public interface IMenuService extends IService<Menu> {

    boolean saveMenu(Menu menu);

    boolean deleteMenuById(Long menuId);

    List<MenuDetailVo> queryMenuById(Long menuId);

    List<MenuDetailVo> queryAllMenus();

    void delRedisCache();
}
