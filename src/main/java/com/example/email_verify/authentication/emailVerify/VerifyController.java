package com.example.email_verify.authentication.emailVerify;

import com.example.email_verify.authentication.emailVerify.dto.VerifyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("verify-email")
public class VerifyController {
    private final VerifyService verifyService;
    @PostMapping("sending")
    public String sendMail(@RequestBody VerifyDto request){
        return verifyService.sendEmail(request);
    }

    @PostMapping
    public String verifyEmail(@RequestBody VerifyDto dto){
        return verifyService.verifyEmail(dto);
    }

    @PutMapping("resend")
    public String resend(@RequestBody VerifyDto dto){
        return verifyService.resend(dto);
    }
}
