package com.coding.blog.web.security.config;

import com.coding.blog.service.entity.Resource;
import com.coding.blog.service.service.IResourceService;
import com.coding.blog.service.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @User Administrator
 * @CreateTime 2023/12/6 14:24
 * @className com.coding.blog.web.security.config.OrtherSecurityConfig
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OtherSecurityConfig extends PearlWebSecurityConfig{
    // @Autowired
    private IUsersService usersService;
    @Autowired
    public void setUsersService(@Lazy IUsersService usersService){
        this.usersService = usersService;
    }

    @Autowired
    private IResourceService resourceService;

    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return userLogin -> usersService.loadUserByUsername(userLogin);
    }

    @Bean
    public DynamicSecurityService dynamicSecurityService() {
        return new DynamicSecurityService() {
            @Override
            public Map<String, ConfigAttribute> loadDataSource() {
                Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
                List<Resource> resources = resourceService.list();
                resources.forEach(item->{
                    map.put(item.getUrl(), new SecurityConfig(item.getResourceId() + ":" + item.getName()));
                });
                return map;
            }
        };
    }
}
