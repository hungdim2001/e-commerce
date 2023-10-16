package com.example.core.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class InvalidRefreshToken  extends RuntimeException{
    private HttpStatus status;
    private String message;
}
