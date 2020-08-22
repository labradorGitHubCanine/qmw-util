package com.qmw.entity;

/**
 * 结果返回类
 *
 * @author qmw
 * @since 1.00
 */
public class ResponseResult<T> {

    private int code;
    private String msg, token;
    private T data;
    private long spent;

    private ResponseResult() {
    }

    // ---------- 静态构造

    public static <T> ResponseResult<T> ok(T data, String msg) {
        return new ResponseResult<T>()
                .setCode(ResponseStatus.OK.getCode())
                .setMsg(msg)
                .setData(data);
    }

    public static <T> ResponseResult<T> ok(T data) {
        return new ResponseResult<T>()
                .setCode(ResponseStatus.OK.getCode())
                .setMsg(ResponseStatus.OK.getMsg())
                .setData(data);
    }

    public static <T> ResponseResult<T> ok() {
        return new ResponseResult<T>()
                .setCode(ResponseStatus.OK.getCode())
                .setMsg(ResponseStatus.OK.getMsg());
    }

    public static <T> ResponseResult<T> error(ResponseStatus status, String msg) {
        return new ResponseResult<T>()
                .setCode(status.getCode())
                .setMsg(msg);
    }

    public static <T> ResponseResult<T> error(ResponseStatus status) {
        return new ResponseResult<T>()
                .setCode(status.getCode())
                .setMsg(status.getMsg());
    }

    // ---------- getters and setters

    public int getCode() {
        return code;
    }

    public ResponseResult<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResponseResult<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public String getToken() {
        return token;
    }

    public ResponseResult<T> setToken(String token) {
        this.token = token;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResponseResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    public long getSpent() {
        return spent;
    }

    public ResponseResult<T> setSpent(long spent) {
        this.spent = spent;
        return this;
    }

}
