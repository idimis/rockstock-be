package com.rockstock.backend.infrastructure.usecase.user.auth.service;

import com.rockstock.backend.infrastructure.usecase.user.auth.dto.RegisterRequestDTO;
import com.rockstock.backend.infrastructure.usecase.user.auth.dto.RegisterResponseDTO;

public interface RegisterUsecase {
    RegisterResponseDTO registerUser(RegisterRequestDTO request);
}
