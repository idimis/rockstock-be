package com.rockstock.backend.service.user.auth;

public interface PasswordResetService {
    void sendResetPasswordEmail(String email);
    void resetPassword(String resetToken, String newPassword);
}
