package com.coding.blog.common.enumapi;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Status {

    /**
     * 业务状态码
     */
    @ApiModelProperty(value = "状态码, 0表示成功返回，其他异常返回", required = true, example = "0")
    private int code;

    /**
     * 描述信息
     */
    @ApiModelProperty(value = "正确返回时为ok，异常时为描述文案", required = true, example = "ok")
    private String msg;

    public static Status newStatus(int code, String msg) {
        return new Status(code, msg);
    }

    public static Status newStatus(StatusEnum status, Object... msgs) {
        String msg;
        if (msgs.length > 0) {
            msg = String.format(status.getMsg(), msgs);
        } else {
            msg = status.getMsg();
        }
        return newStatus(status.getCode(), msg);
    }
}
