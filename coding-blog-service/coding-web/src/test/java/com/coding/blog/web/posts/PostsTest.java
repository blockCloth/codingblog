package com.coding.blog.web.posts;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coding.blog.service.service.IPostsService;
import com.coding.blog.service.vo.FrontPostVo;
import com.coding.blog.web.CodingBlogApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.logging.Logger;

/**
 * @User Administrator
 * @CreateTime 2024/1/31 19:25
 * @className com.coding.blog.web.posts.PostsTest
 */
@SpringBootTest(classes = CodingBlogApplication.class)
@Slf4j
public class PostsTest {
    @Autowired
    private IPostsService postsService;

    @Test
    public void getFrontIntroductionMsg(){
        Page<FrontPostVo> frontIntroductionMsg = postsService.getFrontIntroductionMsg(1, 5);
        for (FrontPostVo record : frontIntroductionMsg.getRecords()) {
            System.out.println(record);
        }
    }
}
