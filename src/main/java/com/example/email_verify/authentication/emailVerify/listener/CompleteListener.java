package com.example.email_verify.authentication.emailVerify.listener;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

// Get generated email, code to implement verification
@Getter
@Setter
public class CompleteListener extends ApplicationEvent {
    private String email;
    private Integer code;

    public CompleteListener(String email, Integer code) {
        super(email);
        this.email= email;
        this.code=code;
    }
}
