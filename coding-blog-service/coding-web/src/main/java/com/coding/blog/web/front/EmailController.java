package com.coding.blog.web.front;

import com.coding.blog.service.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @User Administrator
 * @CreateTime 2024/2/23 19:10
 * @className com.coding.blog.web.front.EmailController
 */
@RestController
@RequestMapping("/front/email")
public class EmailController {
    @Autowired
    private TestService testService;

    @GetMapping("/")
    public void throwException() throws Exception {
        testService.throwException();
    }
}
