package com.devcourse.ReviewRanger.common.config;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import springfox.documentation.oas.web.OpenApiTransformationContext;
import springfox.documentation.oas.web.WebMvcOpenApiTransformationFilter;
import springfox.documentation.spi.DocumentationType;

@Component
public class Workaround implements WebMvcOpenApiTransformationFilter {

	@Override
	public OpenAPI transform(OpenApiTransformationContext<HttpServletRequest> context) {
		OpenAPI openApi = context.getSpecification();
		Server localServer = new Server();
		localServer.setDescription("local");
		localServer.setUrl("http://localhost:8080");

		Server releaseServer = new Server();
		releaseServer.setDescription("release");
		releaseServer.setUrl("https://api.review-ranger.store");
		openApi.setServers(Arrays.asList(localServer, releaseServer));
		return openApi;
	}

	@Override
	public boolean supports(DocumentationType documentationType) {
		return documentationType.equals(DocumentationType.OAS_30);
	}
}
