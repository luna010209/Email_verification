package com.example.email_verify.authentication.emailVerify.listener;

import com.example.email_verify.authentication.emailVerify.repo.VerifyRepo;
import com.example.email_verify.handlerException.CustomException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
@RequiredArgsConstructor
public class CompleteListenerEvent implements ApplicationListener<CompleteListener> {
//    private final VerifyRepo verifyRepo;
    private final JavaMailSender mailSender;
    @Override
    public void onApplicationEvent(CompleteListener event) {
        try{
            String subject = "Email verification for Luna universe";
            String senderName = "Luna Universe";
            String mailContent =
                    "<p>Thank you for your registration with us.<br>" +
                    "This is the verified code for you.</p>" +
                    "<b>" + event.getCode() + "</b>" +
                    "<p> Thank you so much ^^<br><br>" +
                    "Your sincerely, <br> Luna Do";
            MimeMessage message = mailSender.createMimeMessage();
            var messageHelper = new MimeMessageHelper(message);
            messageHelper.setFrom("liendhhha140217@gmail.com", senderName);
            messageHelper.setTo(event.getEmail());
            messageHelper.setSubject(subject);;
            messageHelper.setText(mailContent, true);
            mailSender.send(message);
        } catch(MessagingException | UnsupportedEncodingException e){
            throw new CustomException("Fail to send verified code!!!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
