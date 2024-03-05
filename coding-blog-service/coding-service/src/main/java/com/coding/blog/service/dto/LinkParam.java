package com.coding.blog.service.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @User Administrator
 * @CreateTime 2024/2/20 21:37
 * @className com.coding.blog.service.dto.LinkParam
 */
@Data
@ToString
public class LinkParam implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long linkId;

    @ApiModelProperty("链接URL")
    private String linkUrl;

    @ApiModelProperty("链接标题")
    private String linkName;

    @ApiModelProperty("链接图片")
    private String linkImage;

    @ApiModelProperty("链接描述")
    private String linkDescription;

    @ApiModelProperty("是否可见（Y/N）")
    private String linkVisible;

}
