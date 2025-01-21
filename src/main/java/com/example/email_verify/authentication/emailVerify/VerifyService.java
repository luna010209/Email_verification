package com.example.email_verify.authentication.emailVerify;

import com.example.email_verify.authentication.emailVerify.dto.VerifyDto;
import com.example.email_verify.authentication.emailVerify.entity.EmailVerify;
import com.example.email_verify.authentication.emailVerify.listener.CompleteListener;
import com.example.email_verify.authentication.emailVerify.repo.VerifyRepo;
import com.example.email_verify.handlerException.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class VerifyService {
    private final VerifyRepo verifyRepo;
    private final ApplicationEventPublisher publisher;

    public String sendEmail(VerifyDto request){
        SecureRandom random = new SecureRandom();
        Integer verifiedCode = 100000 + random.nextInt(900000);
        // Implement listener event to send email
        publisher.publishEvent(new CompleteListener(request.getEmail(), verifiedCode));

        // Save into db
        verifyRepo.save(new EmailVerify(request.getEmail(), verifiedCode));
        return "Please check your mail to confirm verified code";
    }

    public String verifyEmail(VerifyDto dto){
        EmailVerify verify = verifyRepo.findByEmail(dto.getEmail()).orElseThrow(
                ()-> new CustomException("No exist email!!!", HttpStatus.NOT_FOUND)
        );
        if (!dto.getCode().equals(verify.getCode()))
            throw new CustomException("Wrong verified code, please check again!!", HttpStatus.UNAUTHORIZED);
        else if ((Date.from(Instant.now())).after(verify.getCodeExpiration()))
            throw new CustomException("An expired code!!!", HttpStatus.UNAUTHORIZED);
        verify.setSuccess(true);
        verifyRepo.save(verify);
        return "Verify successfully!!!";
    }

    public String resend(VerifyDto request){
        EmailVerify verify = verifyRepo.findByEmail(request.getEmail()).orElseThrow(
                ()-> new CustomException("No exist email", HttpStatus.BAD_REQUEST)
        );
        if (verify.isSuccess())
            throw new CustomException("Your email is already verified", HttpStatus.BAD_REQUEST);

        SecureRandom random = new SecureRandom();
        Integer verifiedCode = 100000 + random.nextInt(900000);
        // Implement listener event to send email
        publisher.publishEvent(new CompleteListener(verify.getEmail(), verifiedCode));

        // Save into db
        verify.setCode(verifiedCode);
        verifyRepo.save(verify);
        return "Please check your mail to confirm verified code";
    }
}
