package com.rockstock.backend.service.user.auth;

import com.rockstock.backend.infrastructure.user.auth.dto.OAuthLoginRequest;
import com.rockstock.backend.infrastructure.user.auth.dto.OAuthLoginResponse;
import com.rockstock.backend.infrastructure.user.auth.dto.OAuthRegisterRequest;

public interface OAuthService {
    OAuthLoginResponse handleOAuthLogin(OAuthLoginRequest request);
    OAuthLoginResponse handleOAuthRegister(OAuthRegisterRequest request);
}