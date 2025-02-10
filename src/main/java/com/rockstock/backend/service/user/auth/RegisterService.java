package com.rockstock.backend.service.user.auth;

import com.rockstock.backend.infrastructure.user.auth.dto.RegisterRequestDTO;
import com.rockstock.backend.infrastructure.user.auth.dto.RegisterResponseDTO;

public interface RegisterService {
    RegisterResponseDTO registerUser(RegisterRequestDTO request);
}
