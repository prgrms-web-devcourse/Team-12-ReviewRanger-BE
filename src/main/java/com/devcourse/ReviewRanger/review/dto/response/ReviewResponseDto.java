package com.devcourse.ReviewRanger.review.dto.response;

import java.time.LocalDateTime;

import com.devcourse.ReviewRanger.review.domain.Review;
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
		Review review) {
		this(
			review.getId(),
			review.getTitle(),
			null,
			null,
			null,
			null,
			null
		);
	}
}
