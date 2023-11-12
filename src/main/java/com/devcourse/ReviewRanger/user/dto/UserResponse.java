package com.devcourse.ReviewRanger.user.dto;

import java.time.LocalDateTime;

import com.devcourse.ReviewRanger.user.domain.User;

public record UserResponse(
	Long id,

	String email,

	String name,

	LocalDateTime createdAt,

	LocalDateTime updatedAt
) {
	public static UserResponse toResponse(User user) {
		return new UserResponse(
			user.getId(),
			user.getEmail(),
			user.getName(),
			user.getCreateAt(),
			user.getUpdatedAt()
		);
	}
}
