package com.devcourse.ReviewRanger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableJpaAuditing
@EnableWebSecurity
@EnableAsync
@SpringBootApplication
public class ReviewRangerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReviewRangerApplication.class, args);
	}

}
