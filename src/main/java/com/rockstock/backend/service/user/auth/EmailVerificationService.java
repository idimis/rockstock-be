package com.rockstock.backend.service.user.auth;

public interface EmailVerificationService {
    void sendVerificationEmail(String email);
    void verifyEmail(String verificationToken);
}
