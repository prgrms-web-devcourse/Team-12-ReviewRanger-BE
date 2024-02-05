package com.devcourse.ReviewRanger.common.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.Server;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@OpenAPIDefinition(
	info = @Info(title = "ReviewRanger",
		description = "ReviewRanger API 명세서",
		version = "v1"))
@Configuration
public class SwaggerConfig {

	@Bean
	public Docket api() {
		Server localServer = new Server("local", "http://localhost:8080", "로컬 서버", Collections.emptyList(),
			Collections.emptyList());
		Server releaseServer = new Server("release", "https://api.review-ranger.store", "배포 서버",
			Collections.emptyList(),
			Collections.emptyList());

		return new Docket(DocumentationType.OAS_30)
			.servers(localServer, releaseServer)
			.securityContexts(List.of(this.securityContext())) // SecurityContext 설정
			.securitySchemes(List.of(this.apiKey())) // ApiKey 설정
			.select()
			.apis(RequestHandlerSelectors.basePackage("com.devcourse.ReviewRanger"))
			.paths(PathSelectors.any())
			.build();
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder()
			.securityReferences(defaultAuth())
			.build();
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return List.of(new SecurityReference("Authorization", authorizationScopes));
	}

	// ApiKey 정의
	private ApiKey apiKey() {
		return new ApiKey("Authorization", "Authorization", "header");
	}

	@Bean
	public OpenAPI openAPI() {
		SecurityScheme securityScheme = new SecurityScheme()
			.type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
			.in(SecurityScheme.In.HEADER).name("Authorization");
		SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

		return new OpenAPI()
			.components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
			.security(Arrays.asList(securityRequirement));
	}
}
