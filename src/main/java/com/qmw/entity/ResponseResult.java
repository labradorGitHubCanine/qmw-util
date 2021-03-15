package com.qmw.entity;

import com.qmw.util.StringUtil;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 结果返回类
 *
 * @author qmw
 * @since 1.00
 */
@Data
@Accessors(chain = true)
public class ResponseResult<T> {

    private int code;
    private String msg, token;
    private T data;
    private long spent;

    private ResponseResult() {
    }

    // ---------- 静态构造 ----------

    // 成功
    public static <T> ResponseResult<T> ok(T data, String msg) {
        return new ResponseResult<T>()
                .setCode(ResponseStatus.OK.getCode())
                .setMsg(StringUtil.ifEmptyThen(msg, ResponseStatus.OK.getMsg()))
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
                .setMsg(StringUtil.ifEmptyThen(msg, status.getMsg()));
    }

    // 失败
    public static <T> ResponseResult<T> error(ResponseStatus status) {
        return new ResponseResult<T>()
                .setCode(status.getCode())
                .setMsg(status.getMsg());
    }

    public static <T> ResponseResult<T> error(String msg) {
        return new ResponseResult<T>()
                .setCode(ResponseStatus.ERROR.getCode())
                .setMsg(StringUtil.ifEmptyThen(msg, ResponseStatus.ERROR.getMsg()));
    }

    public static <T> ResponseResult<T> error() {
        return new ResponseResult<T>()
                .setCode(ResponseStatus.ERROR.getCode())
                .setMsg(ResponseStatus.ERROR.getMsg());
    }

}
