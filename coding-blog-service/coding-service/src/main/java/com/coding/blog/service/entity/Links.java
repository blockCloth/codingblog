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
 * 链接信息表
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
@Getter
@Setter
@TableName("links")
@ApiModel(value = "Links对象", description = "链接信息表")
public class Links implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "link_id", type = IdType.AUTO)
    private Long linkId;

    @ApiModelProperty("链接URL")
    @TableField("link_url")
    private String linkUrl;

    @ApiModelProperty("链接标题")
    @TableField("link_name")
    private String linkName;

    @ApiModelProperty("链接图片")
    @TableField("link_image")
    private String linkImage;

    @ApiModelProperty("链接打开方式")
    @TableField("link_target")
    private String linkTarget;

    @ApiModelProperty("链接描述")
    @TableField("link_description")
    private String linkDescription;

    @ApiModelProperty("是否可见（Y/N）")
    @TableField("link_visible")
    private String linkVisible;

    @ApiModelProperty("添加者用户ID")
    @TableField("link_owner")
    private Long linkOwner;
}
