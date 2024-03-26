package com.coding.blog.service.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @User Administrator
 * @CreateTime 2024/3/25 15:53
 * @className com.coding.blog.service.vo.TalkImageVo
 */
@Data
@ToString
public class TalkImageVo {

    @ApiModelProperty("talk_image_id")
    private Long talkImageId;

    @ApiModelProperty("图片属性")
    private String talkImage;
}
