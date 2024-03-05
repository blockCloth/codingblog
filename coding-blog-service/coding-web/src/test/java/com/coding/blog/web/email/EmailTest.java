package com.coding.blog.web.email;

import com.coding.blog.service.service.TestService;
import com.coding.blog.web.CodingBlogApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @User Administrator
 * @CreateTime 2024/2/23 19:01
 * @className com.coding.blog.web.email.EmailTest
 */
@SpringBootTest(classes = CodingBlogApplication.class)
public class EmailTest {
    @Autowired
    private TestService testService;

    @Test
    public void testEmailSend(){
        try {
            testService.throwException();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
