package com.coding.blog.web.admin;

import com.coding.blog.service.entity.Message;
import com.coding.blog.service.service.MessageService;
import com.coding.blog.service.vo.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @User Administrator
 * @CreateTime 2024/3/10 22:20
 * @className com.coding.blog.web.admin.MessageController
 */
@Api(tags = "后台留言、标签管理")
@RestController
@RequestMapping("message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping("getAllList")
    @ApiOperation("获取所有留言")
    public ResultObject getAllList(@RequestBody Map<String, Object> data){
        List<Message> list = messageService.list(data);
        return ResultObject.success(list);
    }

    @PutMapping("update")
    @ApiOperation("修改留言信息")
    public ResultObject updateMessage(@RequestBody Message message){
        if (messageService.updateMessage(message)){
            return ResultObject.success();
        }
        return ResultObject.failed();
    }

    @DeleteMapping("delete")
    @ApiOperation("删除留言信息")
    public ResultObject deleteMessage(Long id){
        if (messageService.deleteMessage(id)){
            return ResultObject.success();
        }
        return ResultObject.failed();
    }

    @PostMapping("saveTag")
    @ApiOperation("保存留言标签")
    public ResultObject saveTag(@RequestBody String tagName){
        if (messageService.saveTag(tagName)){
            return ResultObject.success("保存成功！");
        }
        return ResultObject.failed("保存失败！");
    }

    @GetMapping("getTagList")
    @ApiOperation("查询所有留言标签")
    public ResultObject getTagList(){
        List<String> tags = messageService.getTagList();
        return ResultObject.success(tags);
    }

    @DeleteMapping("deleteTag")
    @ApiOperation("删除留言标签")
    public ResultObject deleteTag(String tagName){
        if (messageService.deleteTag(tagName)){
            return ResultObject.success("删除成功！");
        }
        return ResultObject.failed("删除失败！");
    }
}
