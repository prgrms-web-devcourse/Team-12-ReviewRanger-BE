package com.devcourse.ReviewRanger.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 상세 정보 DTO")
public record UserInfoResponse(
	@Schema(description = "사용자 고유 id")
	Long id,

	@Schema(description = "사용자 이름")
	String name,

	@Schema(description = "사용자 이미지 경로")
	String path,

	@Schema(description = "사용자 이메일")
	String email
) {
}
