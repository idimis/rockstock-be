package com.rockstock.backend;

import com.rockstock.backend.infrastructure.config.JwtConfigProperties;
import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@Log
@SpringBootApplication
@EnableConfigurationProperties(JwtConfigProperties.class)
@EnableCaching
public class BackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
