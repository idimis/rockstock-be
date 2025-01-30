package com.rockstock.backend.usecase.user.auth.service.impl;

import com.rockstock.backend.entity.User;
import com.rockstock.backend.infrastructure.system.security.TokenService;
import com.rockstock.backend.usecase.user.repository.UserRepository;
import com.rockstock.backend.usecase.user.auth.service.EmailVerificationUsecase;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailVerificationUsecaseImpl implements EmailVerificationUsecase {
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final JavaMailSender mailSender;

    public EmailVerificationUsecaseImpl(UserRepository userRepository, TokenService tokenService, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.mailSender = mailSender;
    }

    @Override
    public void sendVerificationEmail(String email) {
        User user = userRepository.findByEmailContainsIgnoreCase(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String token = tokenService.generateVerificationToken(user);  // generate verification token
        String verificationUrl = "http://yourdomain.com/verify-email?token=" + token;  // Your verification URL

        try {
            // Create email content
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(user.getEmail());
            helper.setSubject("Email Verification for Rockstock");
            helper.setText("Please click the link to verify your email: " + verificationUrl, true);

            // Send email
            mailSender.send(message);
        } catch (MessagingException e) {
            // Enhanced error handling
            throw new RuntimeException("Failed to send verification email to " + email, e);
        }
    }

    @Override
    public void verifyEmail(String verificationToken) {
        String email = tokenService.validateVerificationToken(verificationToken);  // Validate token
        User user = userRepository.findByEmailContainsIgnoreCase(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid token"));

        user.setIsVerified(true);  // Mark user as verified
        userRepository.save(user);  // Save updated user
    }
}
