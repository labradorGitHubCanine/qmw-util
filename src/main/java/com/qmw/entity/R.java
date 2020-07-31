package com.qmw.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 结果返回类
 */
@Data
@Accessors(chain = true)
public class R {

    private int code;
    private String msg;
    private String token;
    private Object data;

    private R() {
    }

    public static R ok(Object data, String msg) {
        return new R().setCode(1).setMsg(msg).setData(data);
    }

    public static R ok(Object data) {
        return ok(data, "操作成功");
    }

    public static R ok(String msg) {
        return ok(null, msg);
    }

    public static R ok() {
        return ok(null, "操作成功");
    }

    public static R error(String msg) {
        return new R().setCode(0).setMsg(msg);
    }

    public static R error() {
        return error("操作失败");
    }

    public static R relogin(String msg) {
        return new R().setCode(1000).setMsg(msg);
    }

}
