package com.qmw.entity;

/**
 * 结果返回类
 *
 * @author qmw
 * @since 1.00
 */
public class ResponseResult<T> {

    private static boolean initialized; // 是否已经初始化

    // 成功，失败，需要重新登录，需要更新，接口/链接失效
    private static int OK, ERROR, RELOGIN, UPGRADE, INVALID;

    private int code;
    private String msg, token;
    private T data;

    private ResponseResult() {
        if (!initialized)
            throw new RuntimeException("请先调用" + this.getClass().getName() + ".init方法进行初始化（只需调用一次）");
    }

    public static void init(int ok, int error, int relogin, int upgrade, int invalid) {
        initialized = true;
        OK = ok;
        ERROR = error;
        RELOGIN = relogin;
        UPGRADE = upgrade;
        INVALID = invalid;
    }

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

}
