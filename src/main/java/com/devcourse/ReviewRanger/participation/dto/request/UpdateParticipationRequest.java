package com.devcourse.ReviewRanger.participation.dto.request;

import java.util.List;

import com.devcourse.ReviewRanger.reviewedTarget.dto.request.UpdateReviewedTargetRequest;

public record UpdateParticipationRequest(
	Long participationId,

	List<UpdateReviewedTargetRequest> updateReviewedTargetRequests
) {
}
