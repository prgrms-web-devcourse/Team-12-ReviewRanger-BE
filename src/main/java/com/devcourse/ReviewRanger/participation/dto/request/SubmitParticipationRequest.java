package com.devcourse.ReviewRanger.participation.dto.request;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.devcourse.ReviewRanger.reviewedTarget.dto.request.CreateReviewedTargetRequest;
import com.fasterxml.jackson.annotation.JsonProperty;

public record SubmitParticipationRequest(
	@NotBlank(message = "참여 Id는 빈값 일 수 없습니다.")
	Long participationId,

	@JsonProperty("reviewTargets")
	List<CreateReviewedTargetRequest> createReviewedTargetRequests
) {
}
