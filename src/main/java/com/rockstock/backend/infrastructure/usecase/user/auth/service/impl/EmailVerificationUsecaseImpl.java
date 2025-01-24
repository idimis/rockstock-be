package com.rockstock.backend.infrastructure.usecase.user.auth.service.impl;

import com.rockstock.backend.entity.User;
import com.rockstock.backend.infrastructure.system.security.TokenService;
import com.rockstock.backend.infrastructure.usecase.user.repository.UserRepository;
import com.rockstock.backend.infrastructure.usecase.user.auth.service.EmailVerificationUsecase;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationUsecaseImpl implements EmailVerificationUsecase {
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public EmailVerificationUsecaseImpl(UserRepository userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @Override
    public void sendVerificationEmail(String email) {
        User user = userRepository.findByEmailContainsIgnoreCase(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String token = tokenService.generateVerificationToken(user);
        // Logic to send email (e.g., with a mail service)
    }

    @Override
    public void verifyEmail(String verificationToken) {
        String email = tokenService.validateVerificationToken(verificationToken);
        User user = userRepository.findByEmailContainsIgnoreCase(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid token"));

        user.setIsVerified(true);
        userRepository.save(user);
    }
}
