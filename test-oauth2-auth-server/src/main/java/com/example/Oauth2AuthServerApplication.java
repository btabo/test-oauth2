package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringBootApplication
@EnableAuthorizationServer
public class Oauth2AuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(Oauth2AuthServerApplication.class, args);
	}
}
