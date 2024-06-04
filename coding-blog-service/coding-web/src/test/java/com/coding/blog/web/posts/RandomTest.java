package com.coding.blog.web.posts;

import org.junit.jupiter.api.Test;

import java.util.Random;

/**
 * @User Administrator
 * @CreateTime 2024/6/4 10:09
 * @className com.coding.blog.web.posts.RandomTest
 */
public class RandomTest {

    @Test
    public void random(){
        Random random = new Random();
        int randomNum = random.nextInt((20 - 10) + 1) + 5;
        System.out.println(randomNum);
    }
}
