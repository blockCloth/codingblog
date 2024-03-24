package com.coding.blog.service.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.coding.blog.service.entity.Talk;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @User Administrator
 * @CreateTime 2024/3/24 15:21
 * @className com.coding.blog.service.vo.TalkVo
 */
@Data
@ToString
public class TalkVo {

    @ApiModelProperty("talk_id")
    private Long talkId;

    @ApiModelProperty("说说内容")
    private String talkContent;

    @ApiModelProperty("文章状态")
    private String talkStatus;

    @ApiModelProperty("置顶ID")
    private Integer talkOrder;

    @ApiModelProperty("发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;

    private String talkImages;
}
