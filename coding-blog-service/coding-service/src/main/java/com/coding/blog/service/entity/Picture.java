package com.coding.blog.service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @User Administrator
 * @CreateTime 2024/2/28 10:55
 * @className com.coding.blog.service.entity.Picture
 */
@Getter
@Setter
@TableName("picture")
@ApiModel(value = "Picture对象", description = "后台背景图片表")
public class Picture implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "picture_id", type = IdType.AUTO)
    private Long pictureId;

    @ApiModelProperty("路由名称")
    @TableField("picture_name")
    private String pictureName;


    @ApiModelProperty("图片地址")
    @TableField("picture_url")
    private String pictureUrl;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty("是否显示")
    @TableField("is_visible")
    private Integer isVisible;

}
