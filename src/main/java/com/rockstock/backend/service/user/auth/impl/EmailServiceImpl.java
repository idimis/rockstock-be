package com.rockstock.backend.service.user.auth.impl;

import com.rockstock.backend.service.user.auth.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendVerificationEmail(String toEmail, String verificationLink) throws MessagingException {
        String subject = "Rockstock Email Verification";
        String content = """
                <!DOCTYPE html>
                <html>
                <head>
                    <style>
                        body { font-family: Arial, sans-serif; line-height: 1.6; }
                        .container { max-width: 600px; margin: auto; padding: 20px; }
                        .button { background-color: #000; color: #fff; padding: 10px 20px; text-decoration: none; border-radius: 5px; }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h2>Welcome to Rockstock!</h2>
                        <p>Thank you for registering. Please verify your email address by clicking the button below:</p>
                        <a href="%s" class="button">Verify Email</a>
                        <p>If you did not sign up, please ignore this email.</p>
                        <p>Best,<br>Rockstock Team</p>
                    </div>
                </body>
                </html>
                """.formatted(verificationLink);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(content, true);
        helper.setFrom(fromEmail);

        mailSender.send(message);
    }
}
