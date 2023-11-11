package com.devcourse.ReviewRanger.finalReviewResult.dto;

import java.util.List;

import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResult;

public record CreateFinalReviewRequest(

	Long userId,
	String userName,
	Long reviewId,
	String reviewTitle,
	String reviewDescription,
	List<CreateFinalReplyRequest> replies
) {
	public FinalReviewResult toEntity() {
		return new FinalReviewResult(
			userId,
			userName,
			reviewId,
			reviewTitle,
			reviewDescription
		);
	}
}
