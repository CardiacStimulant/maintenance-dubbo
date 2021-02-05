package com.maintenance.web.exception;

public class DubboManageException extends RuntimeException {
    public DubboManageException() {
        super();
    }

    public DubboManageException(String message) {
        super(message);
    }

    public DubboManageException(String message, Throwable cause) {
        super(message, cause);
    }
}
