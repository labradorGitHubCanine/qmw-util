package com.qmw.entity;

/**
 * 结果返回类
 *
 * @author qmw
 * @since 1.00
 */
public class ResponseResult<T> {

    private static int
            OK = 1, // 成功
            ERROR = 0, // 失败
            RELOGIN = 1000, // 需要重新登录
            UPGRADE = 1001, // 需要更新
            INVALID = 1002, // 接口/链接失效
            UNAUTHORIZED = 1003; // 没有权限

    private int code;
    private String msg, token;
    private T data;
    private long spent;

    private ResponseResult() {
    }

    // ---------- 静态构造

    public static <T> ResponseResult<T> ok(T data, String msg) {
        return new ResponseResult<T>().setCode(OK).setMsg(msg).setData(data);
    }

    public static <T> ResponseResult<T> ok(T data) {
        return ok(data, "操作成功");
    }

    public static <T> ResponseResult<T> ok(String msg) {
        return ok(null, msg);
    }

    public static <T> ResponseResult<T> ok() {
        return ok(null, "操作成功");
    }

    public static <T> ResponseResult<T> error(String msg) {
        return new ResponseResult<T>().setCode(ERROR).setMsg(msg);
    }

    public static <T> ResponseResult<T> error() {
        return error("操作失败");
    }

    public static <T> ResponseResult<T> relogin(String msg) {
        return new ResponseResult<T>().setCode(RELOGIN).setMsg(msg);
    }

    public static <T> ResponseResult<T> upgrade(String msg) {
        return new ResponseResult<T>().setCode(UPGRADE).setMsg(msg);
    }

    public static <T> ResponseResult<T> invalid(String msg) {
        return new ResponseResult<T>().setCode(INVALID).setMsg(msg);
    }

    public static <T> ResponseResult<T> unauthorized(String msg) {
        return new ResponseResult<T>().setCode(UNAUTHORIZED).setMsg(msg);
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

    // ---------- 自定义状态码

    public static void setOK(int OK) {
        ResponseResult.OK = OK;
    }

    public static void setERROR(int ERROR) {
        ResponseResult.ERROR = ERROR;
    }

    public static void setRELOGIN(int RELOGIN) {
        ResponseResult.RELOGIN = RELOGIN;
    }

    public static void setUPGRADE(int UPGRADE) {
        ResponseResult.UPGRADE = UPGRADE;
    }

    public static void setINVALID(int INVALID) {
        ResponseResult.INVALID = INVALID;
    }

    public static void setUNAUTHORIZED(int UNAUTHORIZED) {
        ResponseResult.UNAUTHORIZED = UNAUTHORIZED;
    }

}
