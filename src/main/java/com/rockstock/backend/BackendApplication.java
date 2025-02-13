package com.rockstock.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		System.out.println("POSTGRES_HOST = " + System.getenv("POSTGRES_HOST"));
		System.out.println("POSTGRES_PORT = " + System.getenv("POSTGRES_PORT"));
		System.out.println("POSTGRES_DB = " + System.getenv("POSTGRES_DB"));
		SpringApplication.run(BackendApplication.class, args);
	}

}