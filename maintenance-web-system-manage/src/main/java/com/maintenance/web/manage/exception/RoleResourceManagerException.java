package com.maintenance.web.manage.exception;

public class RoleResourceManagerException extends RuntimeException {
    public RoleResourceManagerException() {
        super();
    }

    public RoleResourceManagerException(String message) {
        super(message);
    }

    public RoleResourceManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleResourceManagerException(Throwable cause) {
        super(cause);
    }
}
