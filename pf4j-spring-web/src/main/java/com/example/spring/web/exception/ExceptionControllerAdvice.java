package com.example.spring.web.exception;

import com.example.spring.web.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(ServiceRuntimeException.class)
    public ResponseEntity<?> handleServiceRuntimeException(ServiceRuntimeException ex) {
        if (log.isInfoEnabled()) {
            log.warn(ex.getMessage(), ex);
        }
        switch (ex.getClass().getSimpleName()) {
            case "PluginNotFoundException":
                return error(HttpStatus.NOT_FOUND, ex);
            default:
                return error(HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private static ResponseEntity<?> error(HttpStatus status, Throwable cause) {
        return ResponseEntity.status(status).body(errorResponseBody(status, cause));
    }

    private static ErrorResponse errorResponseBody(HttpStatus status, Throwable cause) {
        return ErrorResponse.builder()
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(cause.getMessage())
                .build();
    }
}
