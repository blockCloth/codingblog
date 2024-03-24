package com.coding.blog.service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @User Administrator
 * @CreateTime 2024/3/24 11:07
 * @className com.coding.blog.service.entity.TalkImage
 */
@Setter
@Getter
@TableName("talk_image")
@ApiModel(value = "TalkImage对象", description = "说说图片保存地址")
public class TalkImage implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("talk_image_id")
    @TableId(value = "talk_image_id", type = IdType.AUTO)
    private Long talkImageId;

    @ApiModelProperty("图片属性")
    @TableField("talk_image")
    private String talkImage;
}
