package com.coding.blog.web.admin;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.coding.blog.common.enumapi.ResultCode;
import com.coding.blog.service.dto.RoleParam;
import com.coding.blog.service.dto.RoleUpdateParam;
import com.coding.blog.service.entity.Menu;
import com.coding.blog.service.entity.Resource;
import com.coding.blog.service.entity.Role;
import com.coding.blog.service.service.IRoleService;
import com.coding.blog.service.vo.ResultObject;
import com.coding.blog.service.vo.RoleQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 后台用户角色表 前端控制器
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
@Api(tags = "后台角色管理")
@Slf4j
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @ApiOperation("添加角色信息")
    @PostMapping("saveRole")
    public ResultObject addRole(@Validated RoleParam roleParam){
        Role role = new Role();
        BeanUtils.copyProperties(roleParam,role);
        if (role != null && roleService.addRole(role)){
            return ResultObject.success();
        }
        return ResultObject.failed();
    }

    @ApiOperation("修改角色信息")
    @PutMapping("updateRole")
    public ResultObject updateRole(@Validated RoleUpdateParam roleParam){
        Role role = new Role();
        BeanUtils.copyProperties(roleParam,role);
        if (role != null && roleService.updateById(role)) return ResultObject.success();
        return ResultObject.failed();
    }

    @ApiOperation("删除角色信息")
    @DeleteMapping("deleteRole")
    public ResultObject deleteRoleById(Long roleId){
        if (roleId != null && roleService.deleteRoleById(roleId)) return ResultObject.success();
        return ResultObject.failed();
    }

    // @ApiOperation("批量删除角色信息")
    // @DeleteMapping("deleteRoleBatch")
    // public ResultObject deleteRoleBatch(@RequestBody List<Integer> ids){
    //     if ( ids != null && !ids.isEmpty()){
    //         return roleService.deleteRoleBatch(ids) ? ResultObject.success() : ResultObject.failed();
    //     }
    //     return ResultObject.failed();
    // }

    @ApiOperation("查询角色列表")
    @GetMapping("queryRolePages")
    public ResultObject queryRolePages(RoleQueryVo roleQueryVo){
        IPage<Role> roleIPage = roleService.queryRolePages(roleQueryVo);
        if (roleIPage != null) {
            return ResultObject.success(roleIPage);
        }
        return ResultObject.failed();
    }


    @ApiOperation("修改角色状态")
    @PostMapping("updateStatus")
    public ResultObject updateStatus(@RequestParam Integer roleId,
                                       @RequestParam Integer status){
        if (roleId == null) return ResultObject.failed("角色ID不能为空！");
        if (status == null) return ResultObject.failed("status不能为空");

        Role role = roleService.getById(roleId);
        if (role == null) return ResultObject.failed("用户ID不存在！");
        role.setStatus(status);
        if (roleService.updateById(role)) return ResultObject.success();
        return ResultObject.failed();
    }

    @ApiOperation("获取角色相关菜单")
    @GetMapping("listMenu")
    public ResultObject listMenu(@RequestParam Long roleId){
        if (roleId == null) return ResultObject.failed("角色ID不能为空！");

        List<Menu> menus = roleService.listMenus(roleId);

        return ResultObject.success(menus);
    }

    @ApiOperation("获取所有角色")
    @GetMapping("listAll")
    public ResultObject listAll(){

        List<Role> list = roleService.list();

        if (CollUtil.isNotEmpty(list)){
            return ResultObject.success(list);
        }
        return ResultObject.failed();
    }

    @ApiOperation("给角色分配菜单")
    @PostMapping("allocMenu")
    public ResultObject allocMenu(@RequestParam Long roleId,
                                     @RequestParam List<Long> menuIds){
        if (roleId == null) return ResultObject.failed("角色ID不能为空！");
        if (menuIds == null || menuIds.size() == 0) return ResultObject.failed("菜单ID不能为空");

        if (roleService.allocMenu(roleId,menuIds)) return ResultObject.success();
        return ResultObject.failed();
    }

    @ApiOperation("获取角色相关资源")
    @GetMapping("listResource")
    public ResultObject listResource(@RequestParam Long roleId){
        if (roleId == null) return ResultObject.failed("角色ID不能为空！");

        List<Resource> resources = roleService.listResource(roleId);

        return ResultObject.success(resources);
    }
    @ApiOperation("给角色分配资源")
    @PostMapping("allocResources")
    public ResultObject allocResources(@RequestParam Long roleId,
                                  @RequestParam List<Long> resIds){
        if (roleId == null) return ResultObject.failed("角色ID不能为空！");
        if (resIds == null || resIds.size() == 0) return ResultObject.failed("菜单ID不能为空");

        if (roleService.allocResources(roleId,resIds)) return ResultObject.success();
        return ResultObject.failed();
    }
}
