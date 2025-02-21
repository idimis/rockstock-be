package com.rockstock.backend.infrastructure.user.auth.dto;

public class OAuthLoginResponse {
    private String accessToken;
    private String refreshToken;
    private String username;
    private String provider;

    // Constructor to match the parameters used in OAuthServiceImpl
    public OAuthLoginResponse(String accessToken, String refreshToken, String username, String provider) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.username = username;
        this.provider = provider;
    }

    // Getters and Setters
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
