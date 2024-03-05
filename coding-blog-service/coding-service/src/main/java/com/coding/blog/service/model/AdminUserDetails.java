package com.coding.blog.service.model;

import com.coding.blog.common.enumapi.UserStatus;
import com.coding.blog.service.entity.Resource;
import com.coding.blog.service.entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AdminUserDetails implements UserDetails {
    // 后台用户
    private Users users;
    // 拥有资源列表
    private List<Resource> resourceList;
    public AdminUserDetails(Users users) {
        this.users = users;
    }

    public AdminUserDetails(Users users, List<Resource> resourceList) {
        this.users = users;
        this.resourceList = resourceList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的角色
        return resourceList.stream()
                .map(role ->new SimpleGrantedAuthority(role.getResourceId()+":"+role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return users.getUserPass();
    }

    @Override
    public String getUsername() {
        return users.getUserLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return users.getUserStatus().equals(UserStatus.ENABLE.getStatus());
    }

    public Users getUsers(){
        return  this.users;
    }
}