package com.coding.blog.service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 文章
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
@Getter
@Setter
@TableName("posts")
@ApiModel(value = "Posts对象", description = "文章")
public class Posts implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("posts_id")
    @TableId(value = "posts_id", type = IdType.AUTO)
    private Long postsId;

    @ApiModelProperty("对应作者ID")
    @TableField("post_author")
    private Long postAuthor;

    @ApiModelProperty("发布时间")
    @TableField("post_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime postDate;

    @ApiModelProperty("正文")
    @TableField("post_content")
    private String postContent;

    @ApiModelProperty("标题")
    @TableField("post_title")
    private String postTitle;

    @ApiModelProperty("摘录")
    @TableField("post_excerpt")
    private String postExcerpt;

    @ApiModelProperty("文章状态")
    @TableField("post_status")
    private String postStatus;

    @ApiModelProperty("评论状态")
    @TableField("comment_status")
    private String commentStatus;

    @ApiModelProperty("修改时间")
    @TableField("post_modified")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime postModified;

    @ApiModelProperty("排序ID")
    @TableField("menu_order")
    private Integer menuOrder;

    @ApiModelProperty("文章类型（post/page等）")
    @TableField("post_type")
    private Integer postType;

    @ApiModelProperty("评论总数")
    @TableField("comment_count")
    private Long commentCount;

    @ApiModelProperty("属性")
    @TableField("attribute")
    private String attribute;

    @ApiModelProperty("正文html")
    @TableField("html_content")
    private String htmlContent;

    @ApiModelProperty("浏览量")
    @TableField("page_view")
    private Long pageView;

    @ApiModelProperty("转载链接")
    @TableField("post_link")
    private String postLink;

    @ApiModelProperty("文章是否展示")
    @TableField("post_visible")
    private Integer postVisible;
}
