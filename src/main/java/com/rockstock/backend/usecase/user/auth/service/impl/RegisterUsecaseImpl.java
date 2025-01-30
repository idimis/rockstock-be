package com.rockstock.backend.usecase.user.auth.service.impl;

import com.rockstock.backend.entity.User;
import com.rockstock.backend.usecase.user.auth.dto.RegisterRequestDTO;
import com.rockstock.backend.usecase.user.auth.dto.RegisterResponseDTO;
import com.rockstock.backend.usecase.user.repository.UserRepository;
import com.rockstock.backend.usecase.user.auth.service.EmailVerificationUsecase;
import com.rockstock.backend.usecase.user.auth.service.RegisterUsecase;
import lombok.extern.java.Log;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log
@Service
public class RegisterUsecaseImpl implements RegisterUsecase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailVerificationUsecase emailVerificationUsecase;  // Inject email verification service

    public RegisterUsecaseImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailVerificationUsecase emailVerificationUsecase) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailVerificationUsecase = emailVerificationUsecase;
    }

    @Override
    public RegisterResponseDTO registerUser(RegisterRequestDTO request) {
        // Check if email is already registered
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        // Create new user
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setIsVerified(false);  // Initially set to false
        user.setRole("USER");

        // Save user to database
        userRepository.save(user);

        // Send email verification
        emailVerificationUsecase.sendVerificationEmail(user.getEmail());

        // Return response DTO
        return new RegisterResponseDTO(user.getId(), "Registration successful. Please verify your email.");
    }
}
