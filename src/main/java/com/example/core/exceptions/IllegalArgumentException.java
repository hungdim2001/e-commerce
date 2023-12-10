package com.example.core.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
@AllArgsConstructor
public class IllegalArgumentException extends RuntimeException {
    private HttpStatus status;
    private String message;
    }
