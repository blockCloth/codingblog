package com.coding.blog.service.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Site对象", description="站点")
public class SiteParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("站点名称")
    @NotBlank(message = "站点名称不能为空")
    private String siteName;

    @ApiModelProperty("公告")
    private String announcement;

    @ApiModelProperty("简介")
    private String siteIntroduce;

    @ApiModelProperty("站点咨询")
    private String siteConsult;

    @ApiModelProperty("站点备案信息")
    private String siteDesc;


}