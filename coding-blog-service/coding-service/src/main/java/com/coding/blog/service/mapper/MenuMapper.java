package com.coding.blog.service.mapper;

import com.coding.blog.service.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coding.blog.service.vo.MenuDetailVo;

import java.util.List;

/**
 * <p>
 * 后台菜单表 Mapper 接口
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<MenuDetailVo> queryMenuTreeById(Long menuId);

    List<MenuDetailVo> selectAllMenus();
}
