package com.rockstock.backend.service.user.auth.impl;

import com.rockstock.backend.entity.user.EmailVerificationToken;
import com.rockstock.backend.entity.user.User;
import com.rockstock.backend.infrastructure.user.repository.EmailVerificationTokenRepository;
import com.rockstock.backend.infrastructure.user.repository.UserRepository;
import com.rockstock.backend.service.user.auth.EmailVerificationService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EmailVerificationServiceImpl implements EmailVerificationService {

    private final JavaMailSender mailSender;
    private final UserRepository userRepository;
    private final EmailVerificationTokenRepository tokenRepository;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailVerificationServiceImpl(JavaMailSender mailSender, UserRepository userRepository, EmailVerificationTokenRepository tokenRepository) {
        this.mailSender = mailSender;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void sendVerificationEmail(String email) {
        // Cari user berdasarkan email
        User user = userRepository.findByEmailContainsIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Generate token baru
        EmailVerificationToken verificationToken = new EmailVerificationToken(user);
        tokenRepository.save(verificationToken);

        // Buat link verifikasi dengan token
        String verificationLink = "http://localhost:8080/api/v1/user/verify-email?token=" + verificationToken.getToken();

        // Buat email dengan token dalam tombol verifikasi
        String subject = "Rockstock Email Verification";
        String content = """
                <!DOCTYPE html>
                <html>
                <head>
                    <style>
                        body { font-family: Arial, sans-serif; line-height: 1.6; }
                        .container { max-width: 600px; margin: auto; padding: 20px; }
                        .button { background-color: #000; color: #fff; padding: 10px 20px; text-decoration: none; border-radius: 5px; display: inline-block; }
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

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, StandardCharsets.UTF_8.name());
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(content, true);
            helper.setFrom(fromEmail);
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Error sending email", e);
        }
    }

    @Override
    @Transactional
    public void verifyEmail(String token) {
        EmailVerificationToken verificationToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired token"));

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        User user = verificationToken.getUser();
        user.setIsVerified(true);
        userRepository.save(user);

        tokenRepository.delete(verificationToken);
    }
}
