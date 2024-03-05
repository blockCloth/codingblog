package com.coding.blog.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @User Administrator
 * @CreateTime 2023/12/5 19:23
 * @className com.coding.blog.service.dto.UsersRegisterParam
 */
@Data
@ApiModel(value="用户注册", description="用户表")
public class UsersRegisterParam implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "登录名")
    @NotBlank(message="登录名不能为空")
    private String userLogin;

    @ApiModelProperty(value = "密码")
    @NotBlank(message="密码不能为空")
    private String userPass;

    @ApiModelProperty(value = "email")
    @NotBlank(message="邮箱不能为空")
    private String userEmail;

    @ApiModelProperty(value = "昵称")
    @NotBlank(message="昵称不能为空")
    private String userNicename;

    @ApiModelProperty("属性")
    private String attribute;

    @ApiModelProperty(value = "网址")
    private String userUrl;

    @ApiModelProperty(value = "图像")
    private String displayName;

}
