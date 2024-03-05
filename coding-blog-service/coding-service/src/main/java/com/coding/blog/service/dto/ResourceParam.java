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
import java.time.LocalDateTime;

/**
 * @User Administrator
 * @CreateTime 2023/12/10 14:48
 * @className com.coding.blog.service.dto.ResourceParam
 */
@Getter
@Setter
@ApiModel(value = "Resource对象", description = "后台资源表")
public class ResourceParam implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long resourceId;

    @ApiModelProperty("资源分类ID")
    @NotNull(message = "资源分类ID不能为空")
    private Long categoryId;

    @ApiModelProperty("资源名称")
    @NotNull(message = "资源名称不能为空")
    private String name;

    @ApiModelProperty("资源URL")
    @NotNull(message = "资源URL不能为空")
    private String url;

    @ApiModelProperty("描述")
    private String description;
}
