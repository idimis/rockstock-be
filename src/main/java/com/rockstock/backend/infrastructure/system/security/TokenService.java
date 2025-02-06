package com.rockstock.backend.infrastructure.system.security;

import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenService {

//    private final RsaKeyConfigProperties rsaKeyConfigProperties;
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    // Method to generate JWT token with RSA keys
    public String generateToken(Map<String, Object> claims, String subject, long expiration) {
//        RSAKey rsaKey = new RSAKey.Builder(rsaKeyConfigProperties.publicKey()).privateKey(rsaKeyConfigProperties.privateKey()).build();
//        JWKSet jwkSet = new JWKSet(rsaKey);

        // Create JWT token using NimbusJwtEncoder
        return jwtEncoder.encode(jwt -> jwt
                .subject(subject)
                .claims(c -> c.putAll(claims))
                .issuedAt(new Date())
                .expiresAt(new Date(System.currentTimeMillis() + expiration))
        ).getTokenValue();
    }

    // Method to validate the JWT token
    public boolean validateToken(String token) {
        try {
            Jwt jwt = jwtDecoder.decode(token);
            return jwt != null;
        } catch (Exception e) {
            return false;
        }
    }

    // Method to extract the username (subject) from the token
    public String extractUsername(String token) {
        Jwt jwt = jwtDecoder.decode(token);
        return jwt.getSubject();
    }
}
