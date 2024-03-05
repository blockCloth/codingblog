package com.coding.blog.service.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @User Administrator
 * @CreateTime 2024/2/28 11:43
 * @className com.coding.blog.service.dto.PictureParam
 */
@Data
@ToString
public class PictureParam implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long pictureId;

    @ApiModelProperty("图片地址")
    private String pictureUrl;

}
