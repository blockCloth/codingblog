package com.coding.blog.service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 站点配置
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
@Getter
@Setter
@TableName("site")
@ApiModel(value = "Site对象", description = "站点配置")
public class Site implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("site_id")
    @TableId(value = "site_id", type = IdType.AUTO)
    private Long siteId;

    @ApiModelProperty("名称")
    @TableField("site_name")
    private String siteName;

    @ApiModelProperty("公告")
    @TableField("announcement")
    private String announcement;

    @ApiModelProperty("简介")
    @TableField("site_introduce")
    private String siteIntroduce;

    @TableField("site_consult")
    @ApiModelProperty("站点咨询")
    private String siteConsult;

    @TableField("site_desc")
    @ApiModelProperty("站点备案信息")
    private String siteDesc;

    @ApiModelProperty("更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;
}
