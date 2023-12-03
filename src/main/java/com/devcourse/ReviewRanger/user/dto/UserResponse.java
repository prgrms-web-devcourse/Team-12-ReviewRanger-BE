package com.devcourse.ReviewRanger.user.dto;

import java.time.LocalDateTime;

import com.devcourse.ReviewRanger.user.domain.User;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "유저 기본 응답 DTO")
public record UserResponse(
	@Schema(description = "유저 고유 id")
	Long id,

	@Schema(description = "이메일")
	String email,

	@Schema(description = "이름")
	String name,

	@Schema(description = "이미지 경로")
	String path,

	@Schema(description = "생성일")
	LocalDateTime createdAt,

	@Schema(description = "수정일")
	LocalDateTime updatedAt
) {
	public static UserResponse toResponse(User user) {
		return new UserResponse(
			user.getId(),
			user.getEmail(),
			user.getName(),
			user.getImageUrl(),
			user.getCreateAt(),
			user.getUpdatedAt()
		);
	}
}
