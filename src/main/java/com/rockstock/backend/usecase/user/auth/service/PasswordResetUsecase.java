package com.rockstock.backend.usecase.user.auth.service;

public interface PasswordResetUsecase {
    void sendResetPasswordEmail(String email);
    void resetPassword(String resetToken, String newPassword);
}
