package com.example.email_verify.authentication.emailVerify.entity;

import com.example.email_verify.base.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.Instant;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailVerify extends BaseEntity {
    private String email;
    private boolean isSuccess;
    private Date expiration;
    private Integer code;

    public EmailVerify(String email, Integer code){
        this.email= email;
        this.code= code;
        this.isSuccess=false;
        this.expiration= this.getCodeExpiration();
    }

    public Date getCodeExpiration(){
        Instant now = Instant.now();
        return Date.from(now.plusSeconds(60*10));
    }
}
