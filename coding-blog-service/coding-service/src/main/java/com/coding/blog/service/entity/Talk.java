package com.coding.blog.service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 说说
 * </p>
 *
 * @author blockCloth
 * @since 2023-03-24
 */
@Getter
@Setter
@TableName("talk")
@ApiModel(value = "Talk对象", description = "说说")
public class Talk implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("talk_id")
    @TableId(value = "talk_id", type = IdType.AUTO)
    private Long talkId;

    @ApiModelProperty("说说内容")
    @TableField("talk_content")
    private String talkContent;

    @ApiModelProperty("说说头像")
    @TableField("talk_avatar")
    private String talkAvatar;

    @ApiModelProperty("说说发布者")
    @TableField("talk_user")
    private String talkUser;

    @ApiModelProperty("说说状态")
    @TableField("talk_status")
    private String talkStatus;

    @ApiModelProperty("置顶ID")
    @TableField("talk_order")
    private Integer talkOrder;

    @ApiModelProperty("发布时间")
    @TableField("create_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;

}
