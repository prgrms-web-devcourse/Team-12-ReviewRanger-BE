package com.devcourse.ReviewRanger.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.devcourse.ReviewRanger.common.jwt.JwtProvider;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	private final JwtProvider jwtProvider;
	private final String[] permitUrls = {"/login", "/sign-up", "/check-id", "/check-email"};

	public SecurityConfig(JwtProvider jwtProvider) {
		this.jwtProvider = jwtProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
