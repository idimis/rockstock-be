package com.rockstock.backend.infrastructure.system.config;

import com.nimbusds.jose.JOSEException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
public class JwtDecoderConfig {

    private final RsaKeyConfigProperties rsaKeyConfigProperties;

    public JwtDecoderConfig(RsaKeyConfigProperties rsaKeyConfigProperties) {
        this.rsaKeyConfigProperties = rsaKeyConfigProperties;
    }

    @Bean
    public JwtDecoder jwtDecoder() throws JOSEException {
        RSAKey rsaKey = new RSAKey.Builder(rsaKeyConfigProperties.publicKey()).build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(rsaKey));
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build(); // Validasi JWT dengan public key RSA
    }
}
