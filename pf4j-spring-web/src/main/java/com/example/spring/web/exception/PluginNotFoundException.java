package com.example.spring.web.exception;

public class PluginNotFoundException extends ServiceRuntimeException {

    public PluginNotFoundException(String message) {
        super(message);
    }

    public PluginNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
