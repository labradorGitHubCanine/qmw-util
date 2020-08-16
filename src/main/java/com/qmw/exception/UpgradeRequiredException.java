package com.qmw.exception;

public class UpgradeRequiredException extends RuntimeException {

    public UpgradeRequiredException() {
        super();
    }

    public UpgradeRequiredException(String msg) {
        super(msg);
    }

}
