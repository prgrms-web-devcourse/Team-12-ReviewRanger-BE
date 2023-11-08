package com.devcourse.ReviewRanger.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 로그인 응답 DTO")
public record LoginResponse(
	@Schema(name = "access토큰")
	String accessToken,

	@Schema(name = "토큰타입")
	String tokenType
) {
}
