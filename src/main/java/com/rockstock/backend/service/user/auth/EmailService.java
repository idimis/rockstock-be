package com.rockstock.backend.service.user.auth;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendVerificationEmail(String toEmail, String verificationLink) throws MessagingException;
}
