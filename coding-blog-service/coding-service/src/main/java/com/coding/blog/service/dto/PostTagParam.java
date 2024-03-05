package com.coding.blog.service.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @User Administrator
 * @CreateTime 2023/12/12 16:50
 * @className com.coding.blog.service.dto.PostTagParam
 */
@Getter
@Setter
@ApiModel(value = "PostTag对象", description = "标签表")
public class PostTagParam implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("post_tag_id")
    private Long postTagId;

    @ApiModelProperty("标签名称")
    @NotNull(message = "标签名称不能为空")
    private String description;
}
