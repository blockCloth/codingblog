package com.coding.blog.service.service;

import org.springframework.stereotype.Service;

/**
 * @User Administrator
 * @CreateTime 2024/2/23 19:00
 * @className com.coding.blog.service.service.TestServiceImpl
 */
@Service
public class TestServiceImpl implements TestService {
    @Override
    public void throwException() throws Exception {
        for (int i = 0; i < 3; i++) {

                throw new Exception("Exception number " + (i+1));

        }
    }
}
