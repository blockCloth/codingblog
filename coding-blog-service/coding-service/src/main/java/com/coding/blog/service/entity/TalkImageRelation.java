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
@TableName("talk_image_relation")
@ApiModel(value = "TalkImageRelation对象", description = "说说图片关系表")
public class TalkImageRelation implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("talk_image_relation_id")
    @TableId(value = "talk_image_relation_id", type = IdType.AUTO)
    private Long talkImageRelationId;

    @ApiModelProperty("说说ID")
    @TableField(value = "talk_id")
    private Long talkId;


    @ApiModelProperty("说说图片ID")
    @TableField(value = "talk_image_id")
    private Long talkImageId;
}
