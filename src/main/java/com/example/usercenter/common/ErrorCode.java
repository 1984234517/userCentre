package com.example.usercenter.common;

public enum ErrorCode {
    SUCCESS(0, "请求成功", ""), PARAMS_ERROR(4000, "请求参数错误", ""), NULL_ERROR(40001, "请求数据为空", ""),
    NOT_LOGIN(40100, "未登录", ""), NO_AUTH(40101, "无权限", ""), REGISTER_FAIL(50000, "注册失败", ""),
    SYSTEM_ERROR(50001, "系统内部异常", ""), USER_NOT_EXIT(40102, "用户不存在", "");

    /**
     * 状态码REGISTER_F
     */
    private final int code;

    /**
     * 状态码描述
     */
    private final String message;

    /**
     * 详情描述
     */
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
