package com.coding.blog.service.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @User Administrator
 * @CreateTime 2023/12/8 15:59
 * @className com.coding.blog.service.vo.MenuDetailVo
 */
@Getter
@Setter
public class MenuDetailVo implements Serializable {
    private Long menuId;

    private Long parentId;

    private LocalDateTime createTime;

    private String title;

    private Integer level;

    private Integer sort;

    private String name;

    private String icon;

    private Integer hidden;

    private List<MenuDetailVo> children;

    public MenuDetailVo() {
        children = new ArrayList<>();
    }
}
