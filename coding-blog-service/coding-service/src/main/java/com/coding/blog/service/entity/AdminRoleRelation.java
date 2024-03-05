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
 * 后台用户和角色关系表
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
@Getter
@Setter
@TableName("admin_role_relation")
@ApiModel(value = "AdminRoleRelation对象", description = "后台用户和角色关系表")
public class AdminRoleRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "admin_role_relation_id", type = IdType.AUTO)
    private Long adminRoleRelationId;

    @ApiModelProperty("用户id")
    @TableField("users_id")
    private Long usersId;

    @ApiModelProperty("角色id")
    @TableField("role_id")
    private Long roleId;
}
