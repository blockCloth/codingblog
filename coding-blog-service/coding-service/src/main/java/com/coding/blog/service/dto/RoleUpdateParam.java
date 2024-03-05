package com.coding.blog.service.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @User Administrator
 * @CreateTime 2023/12/7 8:27
 * @className com.coding.blog.service.dto.RoleParam
 */
@Getter
@Setter
@TableName("role")
@ApiModel(value = "Role修改对象", description = "后台用户角色表")
public class RoleUpdateParam {
    private static final long serialVersionUID = 1L;

    private Long roleId;

    @ApiModelProperty("名称")
    @NotNull(message = "菜单名不能为空")
    private String name;

    @ApiModelProperty("描述")
    @NotNull(message = "菜单描述不能为空")
    private String description;

    @ApiModelProperty("状态信息")
    @NotNull(message = "用户状态不能为空")
    private Integer status;
}
