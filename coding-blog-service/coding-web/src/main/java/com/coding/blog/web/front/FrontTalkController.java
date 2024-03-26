package com.coding.blog.web.front;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.coding.blog.service.service.ITalkService;
import com.coding.blog.service.vo.ResultObject;
import com.coding.blog.service.vo.TalkVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @User Administrator
 * @CreateTime 2024/3/25 14:43
 * @className com.coding.blog.web.front.FrontTalkController
 */
@Api(tags = "前台说说管理")
@RestController
@RequestMapping("/front/talk")
public class FrontTalkController {
    @Autowired
    private ITalkService talkService;

    @ApiOperation("获取说说列表")
    @PostMapping("listTalks")
    public ResultObject listTalks(@RequestParam Integer current,
                                  @RequestParam Integer size){
        IPage<TalkVo> talkVoIPage = talkService.listFrontTalks(current, size);

        if (ObjectUtil.isNotEmpty(talkVoIPage))
            return ResultObject.success(talkVoIPage);

        return ResultObject.failed();
    }

    @ApiOperation("点赞说说内容")
    @GetMapping("talkLike")
    public ResultObject talkLike(Long talkId){
        if (talkService.talkLike(talkId))
            return ResultObject.success();

        return ResultObject.failed();
    }

    @ApiOperation("点赞说说内容")
    @GetMapping("cancelTalkLike")
    public ResultObject cancelTalkLike(Long talkId){
        if (talkService.cancelTalkLike(talkId))
            return ResultObject.success();

        return ResultObject.failed();
    }
}
