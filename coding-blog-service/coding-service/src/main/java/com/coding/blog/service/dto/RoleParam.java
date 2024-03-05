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
import java.time.LocalDateTime;

/**
 * @User Administrator
 * @CreateTime 2023/12/7 8:27
 * @className com.coding.blog.service.dto.RoleParam
 */
@Getter
@Setter
@TableName("role")
@ApiModel(value = "添加Role对象", description = "后台用户角色表")
public class RoleParam {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("名称")
    @NotNull(message = "菜单名不能为空")
    private String name;

    @ApiModelProperty("描述")
    @NotNull(message = "菜单描述不能为空")
    private String description;
}
