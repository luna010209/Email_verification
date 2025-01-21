package com.example.email_verify.authentication.emailVerify.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class VerifyDto {
    private String email;
    private Integer code;
}
