package com.coding.blog.web.admin;

import cn.hutool.core.collection.CollUtil;
import com.coding.blog.service.dto.TalkParam;
import com.coding.blog.service.entity.Talk;
import com.coding.blog.service.service.ITalkService;
import com.coding.blog.service.vo.ResultObject;
import com.coding.blog.service.vo.TalkVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @User Administrator
 * @CreateTime 2024/3/24 10:06
 * @className com.coding.blog.web.admin.TalkController
 */
@Api(tags = "说说后台管理")
@RestController
@RequestMapping("talk")
public class TalkController {
    @Autowired
    private ITalkService talkService;

    @ApiOperation("保存说说内容")
    @PostMapping("save")
    public ResultObject saveTalk(@RequestBody TalkParam param){
        if (talkService.saveTalk(param))
            return ResultObject.success();

        return ResultObject.failed();
    }

    @ApiOperation("修改说说内容")
    @PutMapping("update")
    public ResultObject updateTalk(@RequestBody TalkParam talkParam){
        if (talkService.updateTalk(talkParam))
            return ResultObject.success();

        return ResultObject.failed();
    }

    @ApiOperation("删除说说内容")
    @DeleteMapping("delete")
    public ResultObject deleteTalk(Long talkId){
        if (talkId == null)
            return ResultObject.failed("删除ID不能为空！");

        if (talkService.deleteTalkById(talkId))
            return ResultObject.success();

        return ResultObject.failed();
    }

    @ApiOperation("获取说说内容")
    @GetMapping("getTalkById")
    public ResultObject getTalkById(Long talkId){
        if (talkId == null)
            return ResultObject.failed("ID不能为空！");

        TalkVo talk = talkService.getTalkById(talkId);
        if (talk != null)
            return ResultObject.success(talk);

        return ResultObject.failed();
    }

    @ApiOperation("获取所有说说内容")
    @GetMapping("listTalks")
    public ResultObject listTalks(){

        List<TalkVo> list = talkService.listTalks();
        if (CollUtil.isNotEmpty(list))
            return ResultObject.success(list);

        return ResultObject.failed();
    }

    @ApiOperation("设置说说可见状态")
    @PutMapping("setTalkStatus")
    public ResultObject setTalkStatus(@RequestParam Long talkId,
                                      @RequestParam Integer talkStatus){
        if (talkService.setTalkStatus(talkId,talkStatus))
            return ResultObject.success();

        return ResultObject.failed();
    }

    @ApiOperation("设置说说置顶状态")
    @PutMapping("setTalkOrder")
    public ResultObject setTalkOrder(@RequestParam Long talkId,
                                      @RequestParam Integer talkOrder){
        if (talkService.setTalkOrder(talkId,talkOrder))
            return ResultObject.success();

        return ResultObject.failed();
    }
}
