package com.coding.blog.web.admin;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.coding.blog.common.util.JwtTokenUtil;
import com.coding.blog.service.dto.UsersLoginParam;
import com.coding.blog.service.dto.UsersRegisterParam;
import com.coding.blog.service.dto.UsersUpdateParam;
import com.coding.blog.service.entity.Role;
import com.coding.blog.service.entity.Users;
import com.coding.blog.service.service.IUsersService;
import com.coding.blog.service.vo.ResultObject;
import com.coding.blog.service.vo.UserDetailVo;
import com.coding.blog.service.vo.UserQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
@Api(tags = "后台用户管理")
@Slf4j
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private IUsersService usersService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @ApiOperation(value = "登录以后返回token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject login(@Validated UsersLoginParam users, BindingResult result) {
        String token = usersService.login(users.getUserLogin(), users.getUserPass());
        if (token == null) {
            return ResultObject.validateFailed("用户名或密码错误");
        }
        // 将 JWT 传递回客户端
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        //获取JWT的过期时间
        Date expirationFromToken = jwtTokenUtil.getExpirationFromToken(token);
        tokenMap.put("expiration",expirationFromToken.getTime());

        return ResultObject.success(tokenMap);
    }

    @ApiOperation(value = "刷新token")
    @RequestMapping(value = "/refreshToken", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject refreshToken(HttpServletRequest request) {
        String oldToken = request.getHeader(tokenHeader);
        String refreshToken = usersService.refreshToken(oldToken);
        if (refreshToken == null) {
            return ResultObject.failed("token已经过期！");
        }
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("token", refreshToken);
        tokenMap.put("tokenHead", tokenHead);
        //获取JWT的过期时间
        Date expirationFromToken = jwtTokenUtil.getExpirationFromToken(refreshToken);
        tokenMap.put("expiration",expirationFromToken.getTime());
        return ResultObject.success(tokenMap);
    }

    @ApiOperation("用户退出系统")
    @PostMapping("/logout")
    public ResultObject logout(){
        return ResultObject.success("退出成功！");
    }

    @ApiOperation("注册用户")
    @PostMapping("/register")
    public ResultObject register(@Validated UsersRegisterParam registerParam){
        Users users = new Users();
        BeanUtils.copyProperties(registerParam,users);
        if (users != null && usersService.register(users)) {
            return ResultObject.success("注册成功！");
        }
        return ResultObject.failed("注册失败！");
    }

    @ApiOperation("用户修改")
    @PutMapping("/update")
    public ResultObject update(@Validated UsersUpdateParam updateParam){
        Users users = new Users();
        BeanUtils.copyProperties(updateParam,users);
        if (users != null && usersService.updateById(users)){
            return ResultObject.success("修改成功！");
        }
        return ResultObject.failed("修改失败！");
    }

    @ApiOperation("用户删除")
    @DeleteMapping("/delete")
    public ResultObject deleteById(Integer userId){
        if (usersService.removeById(userId)){
            return ResultObject.success("用户删除成功！");
        }
        return ResultObject.failed("用户删除失败！");
    }

    @ApiOperation("用户批量删除")
    @DeleteMapping("/deleteBatch")
    public ResultObject deleteBatch(@RequestBody List<Integer> ids){
        if (usersService.deleteBatch(ids)){
            log.info("用户批量删除成功！{}",LocalDateTime.now());
            return ResultObject.success("用户批量删除成功！");
        }
        log.warn("用户批量删除失败！{}",LocalDateTime.now());
        return ResultObject.failed("用户批量删除失败！");
    }

    @ApiOperation("查询用户详细信息")
    @GetMapping("/getUserDetail")
    public ResultObject getUserDetail(@RequestParam Long userId){
        UserDetailVo users = usersService.getUserDetail(userId);
        if (users != null){
            users.setUserPass("");
            return ResultObject.success(users,"查询用户详细信息成功！");
        }
        return ResultObject.failed("查询用户详细信息失败！");
    }

    @ApiOperation("查询当前用户的详细信息")
    @GetMapping("/info")
    public ResultObject info(){
        Map<String, Object> info = usersService.getInfo();
        if (CollUtil.isNotEmpty(info)){
            return ResultObject.success(info);
        }
        return ResultObject.failed();
    }

    @ApiOperation("查询所有用户详细信息")
    @GetMapping("/getAllUserDetail")
    public ResultObject getAllUserDetail(UserQueryVo userQueryVo){
        IPage<Users> allUserDetail = usersService.getAllUserDetail(userQueryVo);
        if (allUserDetail != null){
            return ResultObject.success(allUserDetail);
        }
        return ResultObject.failed();
    }

    @ApiOperation("修改用户状态")
    @PutMapping("/enableOrDisable")
    public ResultObject setUserStatus(@RequestParam Integer userId,@RequestParam Integer status){
        if (userId == null) return ResultObject.failed("用户ID不能为空！");
        if (status != 0 && status != 1) return ResultObject.failed("status不能为空！");

        Users users = usersService.getById(userId);
        users.setUserStatus(status);

        if (usersService.updateById(users)){
            log.info("用户状态修改成功！{}",LocalDateTime.now());
            return ResultObject.success("用户状态修改成功！");
        }
        log.warn("用户状态修改失败！{}",LocalDateTime.now());
        return ResultObject.failed("用户状态修改失败！");
    }

    @ApiOperation("修改用户密码")
    @PutMapping("/updateUserPass")
    public ResultObject updateUserPass(String newPass,String oldPass){

        if (usersService.updateUserPass(newPass,oldPass)){
            log.info("用户密码修改成功！{}",LocalDateTime.now());
            return ResultObject.success("用户密码修改成功！");
        }
        log.warn("用户密码修改失败！{}",LocalDateTime.now());
        return ResultObject.failed("用户密码修改失败！");
    }

    @ApiOperation("获取指定用户的角色")
    @GetMapping("/role/query")
    public ResultObject queryRoles(@RequestParam Long userId){
        return ResultObject.success(usersService.queryRoles(userId));
    }

    @ApiOperation("给用户分配角色")
    @PostMapping("/role/save")
    public ResultObject roleSave(@RequestParam Long userId,
                                 @RequestParam List<Long> roleIds){
        if (usersService.roleSave(userId,roleIds)){
            return ResultObject.success();
        }
        return ResultObject.failed();
    }

    @ApiOperation("取消用户角色")
    @DeleteMapping("/role/remove")
    public ResultObject roleRemove(@RequestParam Long userId,
                                 @RequestParam Long roleId){
        if (usersService.roleRemove(userId,roleId)){
            return ResultObject.success();
        }
        return ResultObject.failed();
    }

    @ApiOperation("获取用户名")
    @GetMapping("")
    public ResultObject queryUserNameById(@RequestParam Long userId){
        Users users = usersService.getById(userId);
        if (users != null){
            return ResultObject.success(users);
        }
        return ResultObject.failed();
    }
}
