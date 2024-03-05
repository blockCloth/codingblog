package com.coding.blog.service.vo;

import lombok.Data;

import java.util.List;

/**
 * @User Administrator
 * @CreateTime 2024/2/2 20:31
 * @className com.coding.blog.service.vo.YearPostVo
 */
@Data
public class YearPostVo {
    private Integer year;
    private List<FrontPostVo> postVoList;
}
