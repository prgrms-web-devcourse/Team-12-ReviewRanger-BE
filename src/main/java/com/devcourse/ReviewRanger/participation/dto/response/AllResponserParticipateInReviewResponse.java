package com.devcourse.ReviewRanger.participation.dto.response;

import java.util.List;

import com.devcourse.ReviewRanger.review.dto.response.ReviewResponseDto;

public record AllResponserParticipateInReviewResponse(
	int responserCount,

	ReviewResponseDto reviewResponseDto,

	List<ResponserResponse> responsers
) {
}
