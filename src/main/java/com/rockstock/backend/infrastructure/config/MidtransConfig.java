package com.rockstock.backend.infrastructure.config;

import com.midtrans.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MidtransConfig {

    @Value("${midtrans.server-key}")
    private String serverKey;

    @Value("${midtrans.client-key}")
    private String clientKey;

    @Value("${midtrans.is-production}")
    private boolean isProduction;

    @Bean
    public Config midtransConfig() {
        return new Config(serverKey, clientKey, isProduction);
    }
}
