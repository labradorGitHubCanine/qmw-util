package com.qmw.exception;

import com.qmw.entity.ResponseStatus;

public class CustomException extends RuntimeException {

    private ResponseStatus status;

    public CustomException(ResponseStatus status, String msg) {
        super(msg);
        this.status = status;
    }

    public CustomException(ResponseStatus status) {
        super(status.getMsg());
        this.status = status;
    }

    public CustomException(String msg) {
        super(msg);
        this.status = ResponseStatus.ERROR;
    }

    public CustomException() {
        super(ResponseStatus.ERROR.getMsg());
        this.status = ResponseStatus.ERROR;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

}
