package com.maintenance.common.exception;

public class UserInfoException extends RuntimeException {
    public UserInfoException() {
        super();
    }

    public UserInfoException(String message) {
        super(message);
    }

    public UserInfoException(String message, Throwable cause) {
        super(message, cause);
    }
}
