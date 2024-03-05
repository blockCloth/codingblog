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
 * 后台角色菜单关系表
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
@Getter
@Setter
@TableName("role_menu_relation")
@ApiModel(value = "RoleMenuRelation对象", description = "后台角色菜单关系表")
public class RoleMenuRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "role_menu_relation_id", type = IdType.AUTO)
    private Long roleMenuRelationId;

    @ApiModelProperty("角色ID")
    @TableField("role_id")
    private Long roleId;

    @ApiModelProperty("菜单ID")
    @TableField("menu_id")
    private Long menuId;
}
