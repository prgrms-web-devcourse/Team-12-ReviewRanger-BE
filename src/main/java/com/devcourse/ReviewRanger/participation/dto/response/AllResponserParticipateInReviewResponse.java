package com.devcourse.ReviewRanger.participation.dto.response;

import java.util.List;

import com.devcourse.ReviewRanger.review.dto.response.ReviewResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "설문에 참여한 모든 응답자 조회 응답 DTO")
public record AllResponserParticipateInReviewResponse(
	@Schema(description = "응답자 수")
	int responserCount,

	@Schema(description = "리뷰 정보 응답 DTO")
	ReviewResponseDto reviewResponseDto,

	@Schema(description = "설문에 참여한 모든 응답자 조회 응답 DTO")
	@JsonProperty("responsers")
	List<ResponserResponse> responserResponses
) {
}
