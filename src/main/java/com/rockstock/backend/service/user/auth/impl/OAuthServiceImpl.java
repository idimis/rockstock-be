package com.rockstock.backend.service.user.auth.impl;

import com.rockstock.backend.entity.user.UserProvider;
import com.rockstock.backend.infrastructure.user.auth.dto.OAuthLoginRequest;
import com.rockstock.backend.infrastructure.user.auth.dto.OAuthLoginResponse;
import com.rockstock.backend.infrastructure.user.auth.dto.OAuthRegisterRequest;
import com.rockstock.backend.entity.user.User;
import com.rockstock.backend.infrastructure.user.repository.UserRepository;
import com.rockstock.backend.service.user.auth.OAuthService;
import com.rockstock.backend.service.user.auth.TokenGenerationService;
import com.rockstock.backend.service.user.auth.TokenGenerationService.TokenType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;
import java.util.Optional;

@Service
public class OAuthServiceImpl implements OAuthService {

    private final UserRepository userRepository;
    private final TokenGenerationService tokenGenerationService;
    private final RestTemplate restTemplate;

    public OAuthServiceImpl(UserRepository userRepository, TokenGenerationService tokenGenerationService, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.tokenGenerationService = tokenGenerationService;
        this.restTemplate = restTemplate;
    }

    @Transactional
    public OAuthLoginResponse handleOAuthLogin(OAuthLoginRequest request) {
        String googleApiUrl = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=" + request.getIdToken();
        String response = restTemplate.getForObject(googleApiUrl, String.class);
        JSONObject json = new JSONObject(response);
        String email = json.getString("email");
        String fullName = json.optString("name", "");

        Optional<User> existingUser = userRepository.findByEmailContainsIgnoreCase(email);
        User user;

        if (existingUser.isPresent()) {
            user = existingUser.get();
        } else {
            throw new RuntimeException("User not found, please register first.");
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), null);
        String accessToken = tokenGenerationService.generateToken(authentication, TokenType.ACCESS);
        String refreshToken = tokenGenerationService.generateToken(authentication, TokenType.REFRESH);

        return new OAuthLoginResponse(accessToken, refreshToken, user.getFullname(), user.getUserProvider().getProvider());
    }

    @Transactional
    public OAuthLoginResponse handleOAuthRegister(OAuthRegisterRequest request) {
        String googleApiUrl = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=" + request.getIdToken();
        String response = restTemplate.getForObject(googleApiUrl, String.class);
        JSONObject json = new JSONObject(response);
        String email = json.getString("email");
        String fullName = json.optString("name", "");

        Optional<User> existingUser = userRepository.findByEmailContainsIgnoreCase(email);

        if (existingUser.isPresent()) {
            throw new RuntimeException("User already exists, please log in.");
        }

        User user = new User();
        user.setEmail(email);
        user.setFullname(fullName);
        UserProvider provider = new UserProvider();
        provider.setProvider("google");
        user.setUserProvider(provider);

        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), null);
        String accessToken = tokenGenerationService.generateToken(authentication, TokenType.ACCESS);
        String refreshToken = tokenGenerationService.generateToken(authentication, TokenType.REFRESH);

        return new OAuthLoginResponse(accessToken, refreshToken, user.getFullname(), user.getUserProvider().getProvider());
    }
}
