package com.coding.blog.service.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.coding.blog.service.entity.Menu;
import com.coding.blog.service.entity.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @User Administrator
 * @CreateTime 2023/12/6 15:28
 * @className com.coding.blog.service.vo.UserDetailVo
 */
@Data
@ToString
@ApiModel(value = "Users视图对象", description = "用户表")
public class UserDetailVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("users_id")
    private Long usersId;

    @ApiModelProperty("登录名")
    private String userLogin;

    @ApiModelProperty("密码")
    private String userPass;

    @ApiModelProperty("昵称")
    private String userNicename;

    @ApiModelProperty("Email")
    private String userEmail;

    @ApiModelProperty("网址")
    private String userUrl;

    @ApiModelProperty("注册时间")
    private LocalDateTime userRegistered;

    @ApiModelProperty("激活码")
    private String userActivationKey;

    @ApiModelProperty("用户状态")
    private Integer userStatus;

    @ApiModelProperty("图像")
    private String displayName;

    @ApiModelProperty("open_id")
    private String openId;

    @ApiModelProperty("属性")
    private String attribute;

    @ApiModelProperty("角色")
    private List<Role> roles;

}
