package com.rockstock.backend.infrastructure.usecase.user.auth.service.impl;

import com.rockstock.backend.entity.User;
import com.rockstock.backend.infrastructure.usecase.user.auth.dto.RegisterRequestDTO;
import com.rockstock.backend.infrastructure.usecase.user.auth.dto.RegisterResponseDTO;
import com.rockstock.backend.infrastructure.usecase.user.repository.UserRepository;
import com.rockstock.backend.infrastructure.usecase.user.auth.service.RegisterUsecase;
import lombok.extern.java.Log;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log
@Service
public class RegisterUsecaseImpl implements RegisterUsecase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterUsecaseImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegisterResponseDTO registerUser(RegisterRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setIsVerified(false);
        user.setRole("USER");

        userRepository.save(user);

        return new RegisterResponseDTO(user.getId(), "Registration successful. Please verify your email.");
    }
}
