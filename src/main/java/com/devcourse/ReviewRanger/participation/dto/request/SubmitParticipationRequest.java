package com.devcourse.ReviewRanger.participation.dto.request;

import java.util.List;

import com.devcourse.ReviewRanger.reviewedTarget.dto.request.CreateReviewedTargetRequest;

public record SubmitParticipationRequest(
	Long participationId,

	List<CreateReviewedTargetRequest> createReviewedTargetRequests
) {
}
