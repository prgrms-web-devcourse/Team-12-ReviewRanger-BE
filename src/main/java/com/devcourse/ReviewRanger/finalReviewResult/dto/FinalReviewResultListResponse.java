package com.devcourse.ReviewRanger.finalReviewResult.dto;

import java.time.LocalDateTime;

import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResult;

public record FinalReviewResultListResponse(
	Long id,
	String title,
	LocalDateTime created_at
) {
	public static FinalReviewResultListResponse from(FinalReviewResult finalReviewResult) {
		return new FinalReviewResultListResponse(
			finalReviewResult.getId(),
			finalReviewResult.getTitle(),
			finalReviewResult.getCreateAt()
		);
	}
}
