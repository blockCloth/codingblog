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
 * @CreateTime 2023/12/8 15:12
 * @className com.coding.blog.service.dto.MenuParam
 */
@Getter
@Setter
@TableName("menu")
@ApiModel(value = "Menu保存对象", description = "后台菜单表")
public class MenuParam implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long menuId;

    @ApiModelProperty("父级ID")
    private Long parentId;

    @ApiModelProperty("菜单名称")
    @NotNull(message = "菜单名不能为空!")
    private String title;

    @ApiModelProperty("前端名称")
    @NotNull(message = "前端菜单名不能为空!")
    private String name;

    @ApiModelProperty("前端图标")
    @NotNull(message = "前端图标名不能为空!")
    private String icon;

}
