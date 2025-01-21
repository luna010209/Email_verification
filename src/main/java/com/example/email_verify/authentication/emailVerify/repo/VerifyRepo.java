package com.example.email_verify.authentication.emailVerify.repo;

import com.example.email_verify.authentication.emailVerify.entity.EmailVerify;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerifyRepo extends JpaRepository<EmailVerify, Long> {
    Optional<EmailVerify> findByEmail(String email);
}
