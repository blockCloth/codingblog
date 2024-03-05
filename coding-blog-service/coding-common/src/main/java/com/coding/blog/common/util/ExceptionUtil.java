package com.coding.blog.common.util;

import com.coding.blog.common.eception.ForumAdviceException;
import com.coding.blog.common.enumapi.StatusEnum;

/**
 * @User Administrator
 * @CreateTime 2023/12/10 19:12
 * @className com.coding.blog.common.util.ExceptionUtil
 */
public class ExceptionUtil {

    public static ForumAdviceException of(StatusEnum statusEnum,Object...msgs){
        throw new ForumAdviceException(statusEnum,msgs);
    }
}
