package com.qmw.entity;

/**
 * 结果返回类
 *
 * @author qmw
 * @since 1.00
 */
public class ResponseResult {

    private int code;
    private String msg;
    private String token;
    private Object data;

    private ResponseResult() {
    }

    public static ResponseResult ok(Object data, String msg) {
        return new ResponseResult().setCode(1).setMsg(msg).setData(data);
    }

    public static ResponseResult ok(Object data) {
        return ok(data, "操作成功");
    }

    public static ResponseResult ok(String msg) {
        return ok(null, msg);
    }

    public static ResponseResult ok() {
        return ok(null, "操作成功");
    }

    public static ResponseResult error(String msg) {
        return new ResponseResult().setCode(0).setMsg(msg);
    }

    public static ResponseResult error() {
        return error("操作失败");
    }

    public static ResponseResult relogin(String msg) {
        return new ResponseResult().setCode(1000).setMsg(msg);
    }

    public int getCode() {
        return code;
    }

    public ResponseResult setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResponseResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public String getToken() {
        return token;
    }

    public ResponseResult setToken(String token) {
        this.token = token;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ResponseResult setData(Object data) {
        this.data = data;
        return this;
    }

}
