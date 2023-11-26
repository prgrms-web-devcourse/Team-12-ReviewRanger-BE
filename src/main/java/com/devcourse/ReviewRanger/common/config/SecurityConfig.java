package com.devcourse.ReviewRanger.common.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.devcourse.ReviewRanger.common.jwt.ExceptionHandlerFilter;
import com.devcourse.ReviewRanger.common.jwt.JwtFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	private final ExceptionHandlerFilter exceptionHandlerFilter;
	private final JwtFilter jwtFilter;

	private final String[] permitUrls = {"/login", "/sign-up", "/members/check-id", "/members/check-email",
		"/swagger-ui", "/swagger-ui/**"};

	public SecurityConfig(ExceptionHandlerFilter exceptionHandlerFilter, JwtFilter jwtFilter) {
		this.exceptionHandlerFilter = exceptionHandlerFilter;
		this.jwtFilter = jwtFilter;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// @Bean
	// public CorsConfigurationSource corsConfigurationSource() {
	// 	CorsConfiguration configuration = new CorsConfiguration();
	//
	// 	configuration.setAllowedOrigins(List.of("http://localhost:3000/"));
	// 	configuration.setAllowedMethods(List.of("*"));
	// 	configuration.addAllowedHeader("*");
	// 	configuration.setAllowCredentials(true);
	//
	// 	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	// 	source.registerCorsConfiguration("/**", configuration);
	// 	return source;
	// }

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		configuration.setAllowedOrigins(List.of("http://localhost:3001", "http://localhost:5173", "http://team12-bucket.s3-website.ap-northeast-2.amazonaws.com"));
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
		configuration.addAllowedHeader("*");
		configuration.setExposedHeaders(List.of("Custom-Header"));
		configuration.setAllowCredentials(true);
		configuration.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.cors().configurationSource(corsConfigurationSource())
			.and()
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.formLogin().disable()
			.httpBasic().disable()
			.authorizeRequests()
			.antMatchers(permitUrls).permitAll()
			.and()
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(exceptionHandlerFilter, JwtFilter.class);

		return http.build();
	}
}
