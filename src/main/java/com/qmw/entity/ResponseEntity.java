package com.qmw.entity;

/**
 * 结果返回类
 *
 * @author qmw
 * @since 1.00
 */
public class ResponseEntity {

    private int code;
    private String msg;
    private String token;
    private Object data;

    private ResponseEntity() {
    }

    public static ResponseEntity ok(Object data, String msg) {
        return new ResponseEntity().setCode(1).setMsg(msg).setData(data);
    }

    public static ResponseEntity ok(Object data) {
        return ok(data, "操作成功");
    }

    public static ResponseEntity ok(String msg) {
        return ok(null, msg);
    }

    public static ResponseEntity ok() {
        return ok(null, "操作成功");
    }

    public static ResponseEntity error(String msg) {
        return new ResponseEntity().setCode(0).setMsg(msg);
    }

    public static ResponseEntity error() {
        return error("操作失败");
    }

    public static ResponseEntity relogin(String msg) {
        return new ResponseEntity().setCode(1000).setMsg(msg);
    }

    public int getCode() {
        return code;
    }

    public ResponseEntity setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResponseEntity setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public String getToken() {
        return token;
    }

    public ResponseEntity setToken(String token) {
        this.token = token;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ResponseEntity setData(Object data) {
        this.data = data;
        return this;
    }

}
