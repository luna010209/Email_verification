package com.example.email_verify.handlerException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@Builder
public class CustomException extends RuntimeException{
    private String message;
    private HttpStatus status;
}
