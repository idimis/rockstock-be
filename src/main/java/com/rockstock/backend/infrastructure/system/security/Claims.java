package com.rockstock.backend.infrastructure.system.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Map;

@Slf4j
public class Claims {

    public static Map<String, Object> getClaimsFromJwt() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt jwt)) {
            log.warn("JWT not found in SecurityContext");
            return Map.of(); // Return empty map instead of throwing error
        }

        return jwt.getClaims();
    }

    public static String getEmailFromJwt() {
        return (String) getClaimsFromJwt().getOrDefault("sub", null);
    }

    public static String getRoleFromJwt() {
        return (String) getClaimsFromJwt().getOrDefault("role", null);
    }

    public static Long getUserIdFromJwt() {
        Object userId = getClaimsFromJwt().get("userId");
        if (userId instanceof Number) {
            return ((Number) userId).longValue();
        } else if (userId instanceof String) {
            return Long.parseLong((String) userId);
        }
        return null;
    }
}
