package com.devcourse.ReviewRanger.review.dto.response;

import java.time.LocalDateTime;

import com.devcourse.ReviewRanger.review.domain.ReviewType;

public record ReviewResponseDto(
	Long reviewId,
	String title,
	String description,
	ReviewType reviewType,
	LocalDateTime closeAt,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {
	public ReviewResponseDto(
		Long reviewId,
		String title,
		ReviewType reviewType,
		LocalDateTime createdAt,
		LocalDateTime updatedAt) {
		this(
			reviewId,
			title,
			null,
			reviewType,
			null,
			createdAt,
			updatedAt
		);
	}
}
