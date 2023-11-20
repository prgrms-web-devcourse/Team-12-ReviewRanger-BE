package com.devcourse.ReviewRanger.finalReviewResult.dto;

import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "모든 수신자가 결과를 받았는지 검증 결과 DTO")
public record CheckFinalResultStatus(
	@Schema(description = "검증 상태")
	Boolean checkStatus,

	@Schema(description = "결과를 가진 user id 목록")
	Set<Long> userId
) {
}
