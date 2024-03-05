package com.coding.blog.service.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.coding.blog.service.entity.Role;
import com.coding.blog.service.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;
import com.coding.blog.service.vo.UserDetailVo;
import com.coding.blog.service.vo.UserQueryVo;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
public interface IUsersService extends IService<Users> {

    String login(String userLogin, String userPass);

    boolean register(Users users);

    UserDetails loadUserByUsername(String userLogin);

    boolean deleteBatch(List<Integer> ids);

    UserDetailVo getUserDetail(Long userId);

    IPage<Users> getAllUserDetail(UserQueryVo userQueryVo);

    boolean updateUserPass(String newPass,String oldPass);

    boolean roleSave(Long userId, List<Long> roleIds);

    List<Role> queryRoles(Long userId);

    boolean roleRemove(Long userId, Long roleId);

    Map<String, Object> getInfo();

    /**
     * 获取当前登录用户ID
     */
    Long getCurrentUserId();

    String refreshToken(String oldToken);
}
