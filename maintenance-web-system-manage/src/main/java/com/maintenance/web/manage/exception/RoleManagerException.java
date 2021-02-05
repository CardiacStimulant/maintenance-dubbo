package com.maintenance.web.manage.exception;

public class RoleManagerException extends RuntimeException {
    public RoleManagerException() {
        super();
    }

    public RoleManagerException(String message) {
        super(message);
    }

    public RoleManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleManagerException(Throwable cause) {
        super(cause);
    }
}
