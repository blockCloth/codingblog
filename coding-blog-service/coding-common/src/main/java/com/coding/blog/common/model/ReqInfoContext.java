package com.coding.blog.common.model;

import lombok.Data;

import java.security.Principal;

/**
 * @User Administrator
 * @CreateTime 2023/12/11 13:48
 * @className com.coding.blog.common.model.ReqInfoContext
 */
public class ReqInfoContext {
    private static ThreadLocal<ReqInfo> contexts = new InheritableThreadLocal<>();

    public static void addReqInfo(ReqInfo reqInfo) {
        contexts.set(reqInfo);
    }

    public static void clear() {
        contexts.remove();
    }

    public static ReqInfo getReqInfo() {
        return contexts.get();
    }

    @Data
    public static class ReqInfo {
        /**
         * 访问的域名
         */
        private String host;
        /**
         * 访问路径
         */
        private String path;
        /**
         * userId
         */
        private Integer userId;
        /**
         * 客户端ip
         */
        private String clientIp;
        /**
         * post 表单参数
         */
        private String payload;
        /**
         * referer
         */
        private String referer;
        /**
         * 设备信息
         */
        private String userAgent;

        private String deviceId;


    }
}
