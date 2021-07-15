package com.liucj.as.api.entity;

public enum ResponseCode {
    RC_SUCCESS(200, "SUCCESS"),
    RC_ERROR(4001, "ERROR"),
    RC_ACCOUNT_INVALID(5001, "账号不存在"),
    RC_PWD_INVALID(5002, "密码错误"),
    RC_NEED_LOGIN(5003, "请先登录"),
    RC_CONFIG_INVALID(80001, "请输入正确配置"),
    RC_USER_FORBID(6001, "用户被禁用"),
    ;
    private int code;
    private String msg;

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int code() {
        return code;
    }

    public String msg() {
        return msg;
    }
}
