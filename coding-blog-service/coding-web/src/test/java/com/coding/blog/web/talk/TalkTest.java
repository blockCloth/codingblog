package com.coding.blog.web.talk;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.coding.blog.service.service.ITalkService;
import com.coding.blog.service.vo.TalkVo;
import com.coding.blog.web.CodingBlogApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @User Administrator
 * @CreateTime 2024/3/24 15:49
 * @className com.coding.blog.web.talk.TalkTest
 */
@Slf4j
@SpringBootTest(classes = CodingBlogApplication.class)
public class TalkTest {
    @Autowired
    private ITalkService talkService;

    @Test
    public void getTalkById(){
        TalkVo talkById = talkService.getTalkById(6L);
        log.info("获取说说{}",talkById);
    }

    @Test
    public void listTalks(){
        List<TalkVo> talkVoList = talkService.listTalks();
        log.info("获取说说{}",talkVoList);
    }

    @Test
    public void listFrontTalks(){
        IPage<TalkVo> talkVoIPage = talkService.listFrontTalks(1, 2);

        log.info("获取说说{}",talkVoIPage);
    }
}
