package com.coding.blog.web.users;

import com.coding.blog.service.dto.UsersRegisterParam;
import com.coding.blog.service.entity.Users;
import com.coding.blog.web.CodingBlogApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @User Administrator
 * @CreateTime 2023/12/6 11:09
 * @className com.coding.blog.web.users.UsersTest
 */
@SpringBootTest(classes = CodingBlogApplication.class)
public class UsersTest {

    @Test
    public void copyProperties(){
        UsersRegisterParam registerParam = new UsersRegisterParam();
        registerParam.setUserLogin("张三");
        registerParam.setUserPass("123456");
        registerParam.setUserNicename("dsdasdadad");

        Users users = new Users();

        BeanUtils.copyProperties(registerParam,users);

        System.out.println(users);
    }
}
