package com.qmw.entity;

public enum ResponseStatus {

    OK(1, "操作成功"),
    ERROR(0, "操作失败"),

    RELOGIN(1000, "您的登录已过期，请重新登录"),
    UPGRADE(1001, "检测到新版本，请先更新"),
    INVALID(1002, "您所访问的链接已经失效"),
    UNAUTHORIZED(1003, "您没有访问权限");

    private int code;
    private String msg;

    ResponseStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
