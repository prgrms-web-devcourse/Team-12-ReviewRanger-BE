package com.devcourse.ReviewRanger.review.dto.response;

import java.time.LocalDateTime;

import com.devcourse.ReviewRanger.participation.domain.DeadlineStatus;
import com.devcourse.ReviewRanger.review.domain.Review;

public record GetReviewResponse(
	Long reviewId,
	String title,
	DeadlineStatus status,
	LocalDateTime createdAt,
	Long responserCount
) {
	public GetReviewResponse(Review review, Long responserCount) {
		this(
			review.getId(),
			review.getTitle(),
			review.getStatus(),
			review.getCreateAt(),
			responserCount
		);
	}
}
