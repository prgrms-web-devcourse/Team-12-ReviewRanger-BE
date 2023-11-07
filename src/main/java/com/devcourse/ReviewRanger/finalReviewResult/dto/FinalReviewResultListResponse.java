package com.devcourse.ReviewRanger.finalReviewResult.dto;

import java.time.LocalDateTime;

import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResult;

public record FinalReviewResultListResponse(
	Long id,
	String title,
	LocalDateTime created_at
) {
	public FinalReviewResultListResponse(FinalReviewResult finalReviewResult) {
		this(
			finalReviewResult.getId(),
			finalReviewResult.getTitle(),
			finalReviewResult.getCreateAt()
		);
	}
}
