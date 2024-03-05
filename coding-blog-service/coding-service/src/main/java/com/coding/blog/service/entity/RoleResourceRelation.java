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
 * 后台角色资源关系表
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
@Getter
@Setter
@TableName("role_resource_relation")
@ApiModel(value = "RoleResourceRelation对象", description = "后台角色资源关系表")
public class RoleResourceRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "role_resource_relation_id", type = IdType.AUTO)
    private Long roleResourceRelationId;

    @ApiModelProperty("角色ID")
    @TableField("role_id")
    private Long roleId;

    @ApiModelProperty("资源ID")
    @TableField("resource_id")
    private Long resourceId;
}
