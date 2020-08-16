package com.qmw.exception;

public class TokenExpiredException extends RuntimeException {

    public TokenExpiredException() {
        super("您的登录已过期，请重新登录");
    }

    public TokenExpiredException(String msg) {
        super(msg);
    }

}
