package com.coding.blog.service.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @User Administrator
 * @CreateTime 2023/12/13 8:28
 * @className com.coding.blog.service.dto.TermTaxonomyParam
 */
@Getter
@Setter
@TableName("term_taxonomy")
@ApiModel(value = "TermTaxonomy后台对象", description = "栏目")
public class TermTaxonomyParam implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    private Long termTaxonomyId;

    @ApiModelProperty("说明")
    private String description;

    @ApiModelProperty("栏目名称")
    @NotNull(message = "专栏名不能为空")
    private String name;

    @ApiModelProperty("父栏目id")
    private Long parentId;
}
