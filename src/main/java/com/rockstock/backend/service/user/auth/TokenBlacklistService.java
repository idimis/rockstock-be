package com.rockstock.backend.service.user.auth;

public interface TokenBlacklistService {
    void blacklistToken(String token, String expiredAt);
    boolean isTokenBlacklisted(String token);
}
