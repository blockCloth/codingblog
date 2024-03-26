package com.coding.blog.service.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @User Administrator
 * @CreateTime 2024/3/24 10:10
 * @className com.coding.blog.service.dto.TalkParam
 */
@Getter
@Setter
@ApiModel(value = "Talk对象", description = "说说")
public class TalkParam implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("talk_id")
    private Long talkId;

    @ApiModelProperty("说说头像")
    private String talkAvatar;

    @ApiModelProperty("说说发布者")
    private String talkUser;

    @ApiModelProperty("说说内容")
    private String talkContent;

    @ApiModelProperty("图片属性")
    private List<String> talkImages;

    @ApiModelProperty("文章状态")
    private String talkStatus;

    @ApiModelProperty("置顶ID")
    private Integer talkOrder;

}
