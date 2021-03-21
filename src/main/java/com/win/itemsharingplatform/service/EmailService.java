package com.win.itemsharingplatform.service;

import com.win.itemsharingplatform.repository.EmailSender;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender {

    private final JavaMailSender mailSender;
    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Override
    @Async
    public void send(String to, String email) {
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,"UTF-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            mailSender.send(mimeMessage);
        }catch (MessagingException e) {
            LOGGER.error("failed to send email",e);
            throw new IllegalStateException("Failed to send email");
        }
    }
}
