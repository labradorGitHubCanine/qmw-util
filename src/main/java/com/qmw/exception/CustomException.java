package com.qmw.exception;

import com.qmw.entity.ResponseStatus;

public class CustomException extends RuntimeException {

    private ResponseStatus status;
//    private String msg;

    public CustomException(ResponseStatus status, String msg) {
        super(msg);
        this.status = status;
//        this.msg = msg;
    }

    public CustomException(ResponseStatus status) {
        super(status.getMsg());
        this.status = status;
//        this.msg = status.getMsg();
    }

    public CustomException(String msg) {
        super(msg);
        this.status = ResponseStatus.ERROR;
//        this.msg = msg;
    }

    public CustomException() {
        super(ResponseStatus.ERROR.getMsg());
        this.status = ResponseStatus.ERROR;
//        this.msg = ResponseStatus.ERROR.getMsg();
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }

}
