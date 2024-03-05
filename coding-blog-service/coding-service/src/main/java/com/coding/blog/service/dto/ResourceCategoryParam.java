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
 * @CreateTime 2023/12/9 16:58
 * @className com.coding.blog.service.dto.ResourceCategoryParam
 */
@Getter
@Setter
@ApiModel(value = "ResourceCategory参数对象", description = "资源分类表")
public class ResourceCategoryParam implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long resourceCategoryId;

    @ApiModelProperty("分类名称")
    @NotNull(message = "分类名称不能为空")
    private String name;

    @ApiModelProperty("sort排序")
    private Integer sort;

}
