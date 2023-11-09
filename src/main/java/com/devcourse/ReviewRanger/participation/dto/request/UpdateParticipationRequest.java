package com.devcourse.ReviewRanger.participation.dto.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.devcourse.ReviewRanger.reviewedTarget.dto.request.UpdateReviewedTargetRequest;
import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateParticipationRequest(
	@NotNull(message = "참여 Id는 Null값 일 수 없습니다.")
	Long participationId,

	@JsonProperty("reviewTargets")
	List<UpdateReviewedTargetRequest> updateReviewedTargetRequests
) {
}
