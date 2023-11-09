package com.devcourse.ReviewRanger.finalReviewResult.dto;

import java.util.List;

import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResult;

public record CreateFinalReviewRequest(
	String receiverName,
	Long reviewId,
	String reviewTitle,
	List<CreateFinalReplyRequest> replies
) {
	FinalReviewResult toEntity(Long userId, String description) {
		return new FinalReviewResult(
			userId,
			receiverName,
			reviewId,
			reviewTitle,
			description
		);
	}
}
