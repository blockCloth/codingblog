package com.coding.blog.web.redis;

import com.coding.blog.common.enumapi.RedisConstants;
import com.coding.blog.common.util.RedisTemplateUtil;
import com.coding.blog.service.entity.Message;
import com.coding.blog.service.entity.Posts;
import com.coding.blog.service.mapper.PostsMapper;
import com.coding.blog.web.CodingBlogApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @User Administrator
 * @CreateTime 2023/12/17 18:47
 * @className com.coding.blog.web.redis.RedisTest
 */
@Slf4j
@SpringBootTest(classes = CodingBlogApplication.class)
public class RedisTest {
    @Autowired
    private RedisTemplateUtil redisTemplateUtil;
    @Autowired
    private PostsMapper postsMapper;

    @Test
    public void getPostView(){
        Map<Object, Object> postView = redisTemplateUtil.hGetAll(RedisConstants.POST_PAGE_VIEW);
        List<Posts> topFivePosts = postView.entrySet().stream()
                .sorted((e1, e2) -> ((Integer)e2.getValue()).compareTo((Integer)e1.getValue()))
                .limit(5)
                .map(entry -> postsMapper.selectById(Long.parseLong((String) entry.getKey())))
                .collect(Collectors.toList());

        System.out.println(topFivePosts);
    }
    @Test
    public void redisStrTest(){
        redisTemplateUtil.set("name","zhangsan");
        redisTemplateUtil.setExpire("age",18,500);
        log.info("获取Redis的name属性{}",redisTemplateUtil.get("name"));
        log.info("获取Redis的age属性{}",redisTemplateUtil.get("age"));
        log.info("获取Redis的age属性的剩余时间{}",redisTemplateUtil.getExpire("age"));

        redisTemplateUtil.set("num",10);
        log.info("对num属性进行自增长{}",redisTemplateUtil.incr("num",10));
        log.info("对num属性进行减量{}",redisTemplateUtil.decr("num",15));

        //删除name和age属性
        List<String> list = new ArrayList<>();
        list.add("age");
        redisTemplateUtil.del("name");
        redisTemplateUtil.del(list);
    }

    @Test
    public void testHash(){
        // redisTemplateUtil.hSet("user","name","zhangsan");
        // redisTemplateUtil.hSet("user","age",18);
        // redisTemplateUtil.hSet("user","sex",false);
        //
        // log.info("获取用户所有属性{}",redisTemplateUtil.hGetAll("user"));

        Map<Object,Object> student = new HashMap<>();
        student.put("name","lisi");
        student.put("age",13);
        student.put("address","江西省抚州市");
        redisTemplateUtil.hSetAll("student",student);
        log.info("获取学生所有属性{}",redisTemplateUtil.hGetAll("student"));

        log.info("获取学生字段的value值{}",redisTemplateUtil.hGet("student","age"));

        log.info("判断是否拥有此字段{}",redisTemplateUtil.hExists("student","sex"));

        log.info("设置age属性的增量{}",redisTemplateUtil.hIncr("student","age",15L));
        log.info("设置age属性的减量{}",redisTemplateUtil.hDecr("student","age",5L));
        log.info("删除address属性{}",redisTemplateUtil.hDel("student","address"));
    }

    @Test
    public void testSet(){
        Set<Object> set = redisTemplateUtil.zRange(RedisConstants.MESSAGE_BOARD, 0, -1);
        List<Message> list = set.stream()
                .map(obj -> (Message) obj)
                .sorted(Comparator.comparing(Message::getLocalDateTime,Comparator.reverseOrder()))
                .collect(Collectors.toList());
        System.out.println(list);
        // Set<ZSetOperations.TypedTuple<Object>> set = redisTemplateUtil.zRangeWithScores(RedisConstants.MESSAGE_BOARD, -1, -1);
        //
        //     Double highestScore = set.iterator().next().getScore();
        // System.out.println(highestScore.longValue());

        // redisTemplateUtil.sAdd("names","张三","李四","王五","赵六");
        // redisTemplateUtil.sAdd("test","haha");
        // log.info("判断集合中是否存在某些数据{}",redisTemplateUtil.sIsMember("names","王五"));
        // log.info("集合的元素数量为{}",redisTemplateUtil.sSize("names"));
        // log.info("将names中的元素移动到test中{}",redisTemplateUtil.sMove("names","test","张三"));
        // log.info("删除test中的haha元素{}",redisTemplateUtil.sRemove("test","haha"));
        // log.info("获取names集合{}",redisTemplateUtil.sMembers("names"));
        // log.info("获取test集合{}",redisTemplateUtil.sMembers("test"));
    }

    @Test
    public void testList(){
        redisTemplateUtil.lPush("interests","踢球","唱歌","学习","打游戏");
        log.info("获取集合中某个元素的下标{}",redisTemplateUtil.lIndex("interests",2));
        log.info("根据下标获取元素{}",redisTemplateUtil.lRange("interests",0,-1));
        log.info("获取集合长度{}",redisTemplateUtil.lLength("interests"));
        log.info("移除元素{}",redisTemplateUtil.lRemove("interests",0,"打游戏"));
        log.info("移除后的元素{}",redisTemplateUtil.lRange("interests",0,-1));
    }
}
