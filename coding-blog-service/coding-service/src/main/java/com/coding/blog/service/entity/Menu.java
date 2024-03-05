package com.coding.blog.service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 后台菜单表
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
@Getter
@Setter
@TableName("menu")
@ApiModel(value = "Menu对象", description = "后台菜单表")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "menu_id", type = IdType.AUTO)
    private Long menuId;

    @ApiModelProperty("父级ID")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty("菜单名称")
    @TableField("title")
    private String title;

    @ApiModelProperty("菜单级数")
    @TableField("level")
    private Integer level;

    @ApiModelProperty("菜单排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty("前端名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("前端图标")
    @TableField("icon")
    private String icon;

    @ApiModelProperty("前端隐藏")
    @TableField("hidden")
    private Integer hidden;
}
