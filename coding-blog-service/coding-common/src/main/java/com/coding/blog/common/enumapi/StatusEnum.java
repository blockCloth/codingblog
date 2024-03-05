package com.coding.blog.common.enumapi;

import lombok.Getter;

/**
 * 异常码规范：
 * xxx - xxx - xxx
 * 业务 - 状态 - code
 * <p>
 * 业务取值
 * - 100 全局
 * - 200 文章相关
 * - 300 系统相关
 * - 400 用户相关
 * <p>
 * 状态：基于http status的含义
 * - 4xx 调用方使用姿势问题
 * - 5xx 服务内部问题
 * <p>
 * code: 具体的业务code
 */
@Getter
public enum StatusEnum {
    SUCCESS(0, "OK"),

    // -------------------------------- 通用

    // 全局传参异常
    ILLEGAL_ARGUMENTS(100_400_001, "参数异常"),
    ILLEGAL_ARGUMENTS_MIXED(100_400_002, "参数异常:%s"),

    // 全局权限相关
    FORBID_ERROR(100_403_001, "无权限"),

    FORBID_ERROR_MIXED(100_403_002, "无权限:%s"),
    FORBID_NOTLOGIN(100_403_003, "未登录"),

    // 全局，数据不存在
    RECORDS_NOT_EXISTS(100_404_001, "记录不存在:%s"),

    // 系统异常
    UNEXPECT_ERROR(100_500_001, "非预期异常:%s"),

    // 图片相关异常类型
    UPLOAD_PIC_FAILED(100_500_002, "图片上传失败！"),

    // --------------------------------

    // 系统-角色相关异常
    SYSTEM_ROLE_NOT_EXISTS(300_404_001,"角色不存在，请联系管理员！"),
    SYSTEM_ROLE_EXISTS(300_404_002,"角色已存在，请勿重试！"),
    //系统-菜单相关异常
    SYSTEM_MENU_NOT_EXISTS(300_404_003,"菜单不存在，请联系管理员！"),
    SYSTEM_MENU_EXISTS(300_404_006,"菜单已存在！"),
    SYSTEM_MENU_IS_CHILDREN(300_404_008,"存在子菜单，禁止删除！"),

    //系统-资源相关异常
    SYSTEM_RESOURCE_NOT_EXISTS(300_404_004,"资源不存在，请联系管理员！"),
    SYSTEM_RESOURCE_EXISTS(300_404_005,"资源已存在！"),
    SYSTEM_DATA_USE(300_500_001,"资源已被使用，请勿重试！"),
    //系统-资源分类相关异常
    SYSTEM_RESOURCE_CATEGORY_EXISTS(300_404_007,"该源分类已存在！"),
    // 系统-专栏异常
    SYSTEM_TERM_IS_CHILDREN(300_404_009,"存在子专栏，禁止删除！"),
    //系统-文章相关异常
    SYSTEM_POST_ID_NOT_EXISTS(300_404_010,"文章ID必须存在！"),
    SYSTEM_POST_NOT_EXISTS(300_404_011,"文章不存在，请勿重试！"),

    // 用户相关异常
    LOGIN_FAILED_MIXED(400_403_001, "登录失败"),

    USER_NOT_EXISTS(400_404_001, "用户不存在"),
    USER_EXISTS(400_404_002, "用户已存在"),
    // 用户登录名重复
    USER_LOGIN_NAME_REPEAT(400_404_003, "用户登录名重复"),
    //--------------------------------------------
    //用户密码错误
    LOGIN_FAILED_PASS(400_500_001,"用户密码不正确！"),
    LOGIN_FAILED_NOT_OLDPASS(400_500_002,"旧密码输入错误，请重新输入"),
    // 待审核
    USER_NOT_AUDIT(400_500_002, "用户未审核");


    private int code;

    private String msg;

    StatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static boolean is5xx(int code) {
        return code % 1000_000 / 1000 >= 500;
    }

    public static boolean is403(int code) {
        return code % 1000_000 / 1000 == 403;
    }

    public static boolean is4xx(int code) {
        return code % 1000_000 / 1000 < 500;
    }
}
