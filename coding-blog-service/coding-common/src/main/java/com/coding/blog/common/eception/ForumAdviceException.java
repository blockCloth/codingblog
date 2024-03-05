package com.coding.blog.common.eception;

import com.coding.blog.common.enumapi.Status;
import com.coding.blog.common.enumapi.StatusEnum;
import lombok.Getter;

/**
 * @User Administrator
 * @CreateTime 2023/12/10 19:00
 * @className com.coding.blog.common.eception.ForumAdviceException
 */
public class ForumAdviceException extends RuntimeException{
    @Getter
    private Status status;

    public ForumAdviceException(){

    }

    public ForumAdviceException(Status status){
        this.status = status;
    }

    public ForumAdviceException(int code,String msg){
        this.status = Status.newStatus(code,msg);
    }

    public ForumAdviceException(StatusEnum statusEnum, Object... args) {
        this.status = Status.newStatus(statusEnum, args);
    }

}
