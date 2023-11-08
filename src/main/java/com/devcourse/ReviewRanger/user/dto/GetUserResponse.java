package com.devcourse.ReviewRanger.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 정보 응답 DTO")
public record GetUserResponse(
	@Schema(description = "사용자 고유 id")
	Long id,

	@Schema(description = "사용자 이름")
	String name
) {
}
