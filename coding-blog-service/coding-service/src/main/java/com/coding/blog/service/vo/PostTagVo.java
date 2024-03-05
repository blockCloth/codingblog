package com.coding.blog.service.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @User Administrator
 * @CreateTime 2023/12/12 18:25
 * @className com.coding.blog.service.vo.PostTagVo
 */
@Data
public class PostTagVo implements Serializable {
    private String title;
    private String userLogin;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime postDate;
}
