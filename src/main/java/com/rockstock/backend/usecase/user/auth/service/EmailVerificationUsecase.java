package com.rockstock.backend.usecase.user.auth.service;

public interface EmailVerificationUsecase {
    void sendVerificationEmail(String email);
    void verifyEmail(String verificationToken);
}
