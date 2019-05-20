package com.example.spring.web.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    private Integer status;
    private String error;
    private String message;
}
