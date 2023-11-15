package com.devcourse.ReviewRanger.finalReviewResult.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "최종 리뷰 결과 생성 응답 DTO")
public record CreateFinalReviewResponse(
	@Schema(description = "리뷰 대상 id")
	Long userId
) {
}
