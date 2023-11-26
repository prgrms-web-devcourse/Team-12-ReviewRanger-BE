package com.devcourse.ReviewRanger.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration
// public class WebConfig implements WebMvcConfigurer {
// 	@Override
// 	public void addCorsMappings(CorsRegistry registry) {
// 		registry.addMapping("/**")
// 			.allowedOriginPatterns(
// 				"http://localhost:3001",
// 				"http://localhost:5173/",
// 				"http://team12-bucket.s3-website.ap-northeast-2.amazonaws.com/"
// 			)
// 			.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
// 			.allowedHeaders("Authorization", "Content-Type")
// 			.exposedHeaders("Custom-Header")
// 			.allowCredentials(true)
// 			.maxAge(3600);
// 	}
// }
