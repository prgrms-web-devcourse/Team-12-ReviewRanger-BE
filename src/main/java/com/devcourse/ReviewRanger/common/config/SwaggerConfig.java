package com.devcourse.ReviewRanger.common.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
	info = @Info(title = "리뷰레인저 서비스",
		description = "리뷰레인저 api 명세서",
		version = "v1"))
@Configuration
public class SwaggerConfig {

	@Bean
	public GroupedOpenApi userOpenApi() {
		String[] paths = {"/members/**", "/sign-up", "/login"};

		return GroupedOpenApi.builder()
			.group("유저 API v1")
			.pathsToMatch(paths)
			.build();
	}

	@Bean
	public GroupedOpenApi surveyOpenApi() {
		String[] paths = {"/surveys/**"};

		return GroupedOpenApi.builder()
			.group("설문 API v1")
			.pathsToMatch(paths)
			.build();
	}

	@Bean
	public GroupedOpenApi responseOpenApi() {
		String[] paths = {"/responses/**", "/invited-surveys"};

		return GroupedOpenApi.builder()
			.group("응답 API v1")
			.pathsToMatch(paths)
			.build();
	}
}
