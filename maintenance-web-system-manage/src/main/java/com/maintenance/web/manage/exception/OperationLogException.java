package com.maintenance.web.manage.exception;

public class OperationLogException extends RuntimeException {
    public OperationLogException() {
        super();
    }

    public OperationLogException(String message) {
        super(message);
    }

    public OperationLogException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperationLogException(Throwable cause) {
        super(cause);
    }
}
