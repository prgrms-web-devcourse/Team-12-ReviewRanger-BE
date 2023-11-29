package com.devcourse.ReviewRanger.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 로그인 응답 DTO")
public record LoginResponse(
	@Schema(name = "access 토큰", description = "access 토큰")
	String accessToken,

	@Schema(name = "토큰 타입", description = "토큰 타입")
	String tokenType
) {
}
