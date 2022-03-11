package com.qmw.exception;

import com.qmw.entity.ResponseStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
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

    public static void throwIf(boolean condition, String info) {
        if (condition)
            throw new CustomException(info);
    }

    public static void throwIf(boolean condition) {
        if (condition)
            throw new CustomException();
    }


}
