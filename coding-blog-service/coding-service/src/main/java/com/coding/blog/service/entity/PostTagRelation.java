package com.coding.blog.service.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
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
 * 标签文章关系表
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
@Getter
@Setter
@TableName("post_tag_relation")
@ApiModel(value = "PostTagRelation对象", description = "标签文章关系表")
public class PostTagRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("对应文章ID")
    @TableId("post_id")
    private Long postId;

    @ApiModelProperty("标签ID")
    @TableField(value = "post_tag_id", insertStrategy = FieldStrategy.IGNORED)
    private Long postTagId;

    @ApiModelProperty("排序")
    @TableField("term_order")
    private Integer termOrder;
}
