package com.maintenance.web.manage.exception;

public class UserManagerException extends RuntimeException {
    public UserManagerException() {
        super();
    }

    public UserManagerException(String message) {
        super(message);
    }

    public UserManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserManagerException(Throwable cause) {
        super(cause);
    }
}
