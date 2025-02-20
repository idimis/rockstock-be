package com.rockstock.backend.common.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class SecurityUtils {

    private SecurityUtils() {
        // Private constructor to prevent instantiation
    }

    public static Long getAuthenticatedUserId() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return jwt.getClaim("userId");
    }
}
