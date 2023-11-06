package com.devcourse.ReviewRanger.review.dto.response;

import java.time.LocalDateTime;

import com.devcourse.ReviewRanger.participation.domain.DeadlineStatus;
import com.devcourse.ReviewRanger.review.domain.Review;
import com.devcourse.ReviewRanger.review.domain.ReviewType;

public record ReviewResponse(
	Long reviewId,
	String title,
	String description,
	DeadlineStatus deadlineStatus,
	ReviewType reviewType,
	LocalDateTime createdAt,
	Long responserCount
) {
	public ReviewResponse(Review review, Long responserCount) {
		this(
			review.getId(),
			review.getTitle(),
			review.getDescription(),
			review.getStatus(),
			review.getType(),
			review.getCreateAt(),
			responserCount
		);
	}
}
