package com.devcourse.ReviewRanger.common.config;

import static org.springframework.security.config.Customizer.*;

import javax.servlet.DispatcherType;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().disable()
			.authorizeHttpRequests(request -> request
					.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
					.anyRequest().permitAll()
				// .anyRequest().authenticated()
			)
			// .formLogin(login -> login    // form 방식 로그인 사용
			// 	.defaultSuccessUrl("/view/dashboard", true)    // 성공 시 dashboard로
			// 	.permitAll()    // 대시보드 이동이 막히면 안되므로 얘는 허용
			// )
			.logout(withDefaults());

		return http.build();
	}
}
