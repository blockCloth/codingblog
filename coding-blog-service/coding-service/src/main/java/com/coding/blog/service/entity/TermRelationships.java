package com.coding.blog.service.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
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
 * 文章栏目关系表
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
@Getter
@Setter
@TableName("term_relationships")
@ApiModel(value = "TermRelationships对象", description = "文章栏目关系表")
public class TermRelationships implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("对应文章ID/链接ID")
    @TableId("term_relationships_id")
    private Long termRelationshipsId;

    @ApiModelProperty("栏目ID")
    @TableField(value = "term_taxonomy_id", insertStrategy = FieldStrategy.IGNORED)
    private Long termTaxonomyId;

    @ApiModelProperty("排序")
    @TableField("term_order")
    private Integer termOrder;

    @ApiModelProperty("类型,0:内容,1:链接")
    @TableField(value = "type", insertStrategy = FieldStrategy.IGNORED)
    private Integer type;
}
