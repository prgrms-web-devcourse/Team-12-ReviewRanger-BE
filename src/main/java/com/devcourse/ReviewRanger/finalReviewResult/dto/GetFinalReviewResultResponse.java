package com.devcourse.ReviewRanger.finalReviewResult.dto;

import static com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResult.*;

import java.time.LocalDateTime;

import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResult;

public record GetFinalReviewResultResponse(
	Long userId,
	String userName,
	Long reviewId,
	String title,
	String description,
	Status status,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {
	public GetFinalReviewResultResponse(FinalReviewResult entity) {
		this(
			entity.getUserId(),
			entity.getUserName(),
			entity.getReviewId(),
			entity.getTitle(),
			entity.getDescription(),
			entity.getStatus(),
			entity.getCreateAt(),
			entity.getUpdatedAt()
		);
	}
}
