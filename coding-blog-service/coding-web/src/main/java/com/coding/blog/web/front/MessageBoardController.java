package com.coding.blog.web.front;

import cn.hutool.core.collection.CollectionUtil;
import com.coding.blog.common.properties.sensitive.SensitiveService;
import com.coding.blog.service.dto.MessageParam;
import com.coding.blog.service.entity.Message;
import com.coding.blog.service.service.MessageService;
import com.coding.blog.service.vo.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @User Administrator
 * @CreateTime 2024/3/10 9:53
 * @className com.coding.blog.web.front.MessageBoardController
 */
@Api(tags = "前台留言板管理")
@RestController
@RequestMapping("front/message")
public class MessageBoardController {
    @Autowired
    private SensitiveService sensitiveService;
    @Autowired
    private MessageService messageService;

    @GetMapping("whetherViolation")
    @ApiOperation("判断是否包含敏感词")
    public ResultObject whetherViolation(String msg){
        if (!sensitiveService.contains(msg)) {
            return ResultObject.success();
        }
        String replace = sensitiveService.replace(msg);
        return ResultObject.failed(replace);
    }

    @PostMapping("save")
    @ApiOperation("保存留言信息")
    public ResultObject saveMessage(@RequestBody Message message){
        //判断是否包含敏感词
        if (sensitiveService.contains(message.getMessage()))
            return ResultObject.failed("留言内容违规，请进行修改！");
        if (messageService.saveMessage(message)){
            return ResultObject.success();
        }
        return ResultObject.failed();
    }

    @PostMapping("getList")
    @ApiOperation("获取所有留言信息")
    public ResultObject getMessageList(@RequestBody Map<String, Object> data){
        List<Message> list = messageService.list(data);
        return ResultObject.success(list);
    }

    @GetMapping("getHotTagList")
    @ApiOperation("获取留言热门标签")
    public ResultObject getHotTagList(){
        List<String> tagList = messageService.getTagList();
        if (CollectionUtil.isNotEmpty(tagList)){
            return ResultObject.success(tagList);
        }
        return ResultObject.failed();
    }
}
