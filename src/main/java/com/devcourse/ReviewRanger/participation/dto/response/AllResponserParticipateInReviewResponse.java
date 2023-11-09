package com.devcourse.ReviewRanger.participation.dto.response;

import java.util.List;

import com.devcourse.ReviewRanger.review.dto.response.ReviewResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;

public record AllResponserParticipateInReviewResponse(
	int responserCount,

	ReviewResponseDto reviewResponseDto,

	@JsonProperty("responsers")
	List<ResponserResponse> responserResponses
) {
}
