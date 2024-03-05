package com.coding.blog.service.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @User Administrator
 * @CreateTime 2023/12/6 14:10
 * @className com.coding.blog.service.dto.UsersUpdateParam
 */
@Getter
@Setter
@ToString
@TableName("users")
@ApiModel(value = "用户修改", description = "用户表")
public class UsersUpdateParam implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long usersId;

    @ApiModelProperty("昵称")
    @NotBlank(message="昵称不能为空")
    private String userNicename;

    @ApiModelProperty("Email")
    private String userEmail;

    @ApiModelProperty("网址")
    private String userUrl;

    @ApiModelProperty("图像")
    private String displayName;

}
