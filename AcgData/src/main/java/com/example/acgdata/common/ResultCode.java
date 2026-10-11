package com.example.acgdata.common;

import lombok.Getter;

/**
 * 业务状态码字典。
 * 2xx 成功、4xx 客户端错误、5xx 服务端错误、1xxx 业务语义错误。
 */
@Getter
public enum ResultCode {

    SUCCESS(200, "操作成功"),
    PARAM_ERROR(400, "参数校验失败"),
    UNAUTHORIZED(401, "未登录或登录状态已过期"),
    FORBIDDEN(403, "没有权限执行该操作"),
    NOT_FOUND(404, "请求的资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方式不支持"),
    ERROR(500, "服务器内部错误"),

    LOGIN_FAILED(1001, "用户名或密码错误"),
    USERNAME_EXISTS(1002, "用户名已存在"),
    EMAIL_EXISTS(1003, "邮箱已被注册"),
    USER_NOT_FOUND(1004, "用户不存在"),
    DATA_NOT_FOUND(1005, "数据不存在或已被删除"),
    OPERATION_FAILED(1006, "操作失败"),
    REPEAT_OPERATION(1007, "请勿重复操作");

    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
