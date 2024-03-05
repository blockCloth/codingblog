package com.coding.blog.web.admin;

import com.coding.blog.service.dto.MenuParam;
import com.coding.blog.service.entity.Menu;
import com.coding.blog.service.service.IMenuService;
import com.coding.blog.service.vo.MenuDetailVo;
import com.coding.blog.service.vo.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 后台菜单表 前端控制器
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
@Api(tags = "后台菜单管理")
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private IMenuService menuService;

    @ApiOperation("添加菜单信息")
    @PostMapping("/saveMenu")
    public ResultObject saveMenu(MenuParam menuParam){
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuParam,menu);
        if (menu != null && menuService.saveMenu(menu)){
            return ResultObject.success();
        }
        return ResultObject.failed();
    }

    @ApiOperation("修改菜单信息")
    @PutMapping("updateMenu")
    public ResultObject updateMenu(MenuParam menuParam){
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuParam,menu);
        if (menu != null && menuService.updateById(menu)){
            menuService.delRedisCache();
            return ResultObject.success();
        }
        return ResultObject.failed();
    }

    @ApiOperation("删除菜单信息")
    @DeleteMapping("deleteMenu")
    public ResultObject deleteMenuById(Long menuId){

        if (menuId != null && menuService.deleteMenuById(menuId)){
            return ResultObject.success();
        }
        return ResultObject.failed();
    }

    @ApiOperation("查询菜单详细信息")
    @GetMapping("queryMenuById")
    public ResultObject queryMenuById(Long menuId){
        List<MenuDetailVo> menuDetailVos = menuService.queryMenuById(menuId);
        if (menuDetailVos != null && menuDetailVos.size() > 0){
            return ResultObject.success(menuDetailVos);
        }
        return ResultObject.failed();
    }

    @ApiOperation("查询所有菜单详细信息")
    @GetMapping("queryAllMenus")
    public ResultObject queryAllMenus(){
        List<MenuDetailVo> menuDetailVos = menuService.queryAllMenus();

        if (menuDetailVos != null && menuDetailVos.size() > 0){
            return ResultObject.success(menuDetailVos);
        }
        return ResultObject.failed();
    }

    @ApiOperation("设置菜单是否隐藏")
    @PutMapping("setMenuHidden")
    public ResultObject setMenuHidden(Long menuId,Integer hidden){
        if ((menuId == null || hidden == null) || (hidden != 0 && hidden != 1))
            return ResultObject.failed("请填写正确的参数！");

        //隐藏之前删除Redis缓存
        menuService.delRedisCache();
        Menu menu = menuService.getById(menuId);
        menu.setHidden(hidden);

        return menuService.updateById(menu) ? ResultObject.success() : ResultObject.failed();
    }
}
