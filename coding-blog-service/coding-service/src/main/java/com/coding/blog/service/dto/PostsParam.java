package com.coding.blog.service.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @User Administrator
 * @CreateTime 2023/12/14 8:15
 * @className com.coding.blog.service.dto.PostsParam
 */
@Getter
@Setter
@ApiModel(value = "Posts操作对象", description = "文章")
public class PostsParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("posts_id")
    private Long postsId;

    @ApiModelProperty("正文")
    @NotNull(message = "文章正文不能为空")
    private String postContent;

    @ApiModelProperty("标题")
    @NotNull(message = "文章标题不能为空")
    private String postTitle;

    @ApiModelProperty("摘录")
    @NotNull(message = "文章摘录不能为空")
    private String postExcerpt;

    @ApiModelProperty("评论状态")
    private String commentStatus;


    @ApiModelProperty("排序ID")
    private Integer menuOrder;

    @ApiModelProperty(value = "文章状态：PUBLISHED,DELETED,DRAFT")
    @NotBlank(message = "文章状态不能为空")
    private String postStatus;

    @ApiModelProperty("评论总数")
    private Long commentCount;

    @ApiModelProperty("属性")
    private String attribute;

    @ApiModelProperty("正文html")
    @NotNull(message = "正文HTML不能为空")
    private String htmlContent;

    @ApiModelProperty("标签属性")
    private List<Long> tags;

    @ApiModelProperty("专栏ID")
    private Long termTaxonomyId;

}
