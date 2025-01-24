package com.rockstock.backend.infrastructure.usecase.user.auth.service;

public interface EmailVerificationUsecase {
    void sendVerificationEmail(String email);
    void verifyEmail(String verificationToken);
}
