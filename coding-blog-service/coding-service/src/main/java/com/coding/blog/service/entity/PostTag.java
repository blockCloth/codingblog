package com.coding.blog.service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 标签表
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
@Getter
@Setter
@TableName("post_tag")
@ApiModel(value = "PostTag对象", description = "标签表")
public class PostTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("post_tag_id")
    @TableId(value = "post_tag_id", type = IdType.AUTO)
    private Long postTagId;

    @ApiModelProperty("标签名称")
    @TableField("description")
    private String description;
}
