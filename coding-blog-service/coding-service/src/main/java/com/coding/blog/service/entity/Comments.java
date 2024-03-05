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
 * 评论表
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
@Getter
@Setter
@TableName("comments")
@ApiModel(value = "Comments对象", description = "评论表")
public class Comments implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "comment_id", type = IdType.AUTO)
    private Long commentId;

    @ApiModelProperty("对应文章ID")
    @TableField("comment_post_id")
    private Long commentPostId;

    @ApiModelProperty("评论者")
    @TableField("comment_author")
    private String commentAuthor;

    @ApiModelProperty("评论者邮箱")
    @TableField("comment_author_email")
    private String commentAuthorEmail;

    @ApiModelProperty("评论者网址")
    @TableField("comment_author_url")
    private String commentAuthorUrl;

    @ApiModelProperty("评论者IP")
    @TableField("comment_author_ip")
    private String commentAuthorIp;

    @ApiModelProperty("评论时间")
    @TableField("comment_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime commentDate;

    @ApiModelProperty("评论正文")
    @TableField("comment_content")
    private String commentContent;

    @ApiModelProperty("评论是否被批准")
    @TableField("comment_approved")
    private String commentApproved;

    @ApiModelProperty("评论者的USER AGENT")
    @TableField("comment_agent")
    private String commentAgent;

    @ApiModelProperty("评论类型(pingback/普通)")
    @TableField("comment_type")
    private String commentType;

    @ApiModelProperty("父评论ID")
    @TableField("comment_parent")
    private Long commentParent;

    @ApiModelProperty("评论者用户ID（不一定存在）")
    @TableField("user_id")
    private Long userId;
}
