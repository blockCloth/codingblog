package com.coding.blog.service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
@Getter
@Setter
@ToString
@TableName("users")
@ApiModel(value = "Users对象", description = "用户表")
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("users_id")
    @TableId(value = "users_id", type = IdType.AUTO)
    private Long usersId;

    @ApiModelProperty("登录名")
    @TableField("user_login")
    private String userLogin;

    @ApiModelProperty("密码")
    @TableField("user_pass")
    private String userPass;

    @ApiModelProperty("昵称")
    @TableField("user_nicename")
    private String userNicename;

    @ApiModelProperty("Email")
    @TableField("user_email")
    private String userEmail;

    @ApiModelProperty("网址")
    @TableField("user_url")
    private String userUrl;

    @ApiModelProperty("注册时间")
    @TableField("user_registered")
    private LocalDateTime userRegistered;

    @ApiModelProperty("激活码")
    @TableField("user_activation_key")
    private String userActivationKey;

    @ApiModelProperty("用户状态")
    @TableField("user_status")
    private Integer userStatus;

    @ApiModelProperty("图像")
    @TableField("display_name")
    private String displayName;

    @ApiModelProperty("用户类型 0 :后台 1：前端")
    @TableField("user_type")
    private Integer userType;

    @ApiModelProperty("open_id")
    @TableField("open_id")
    private String openId;

    @ApiModelProperty("属性")
    @TableField("attribute")
    private String attribute;

}
