package com.example.spring.web.exception;

public class ServiceRuntimeException extends RuntimeException {

    public ServiceRuntimeException() {
        super();
    }

    public ServiceRuntimeException(String message) {
        super(message);
    }

    public ServiceRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceRuntimeException(Throwable cause) {
        super(cause);
    }
}
