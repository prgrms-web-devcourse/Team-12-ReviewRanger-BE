package com.devcourse.ReviewRanger.finalReviewResult.dto;

import java.time.LocalDateTime;

import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResult;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "전송 된 최종 리뷰 결과 목록 응답 DTO")
public record FinalReviewResultListResponse(
	@Schema(description = "최종 리뷰 결과 id")
	Long id,

	@Schema(description = "최종 리뷰 결과 title")
	String title,

	@Schema(description = "최종 리뷰 결과 생성 날짜")
	LocalDateTime created_at
) {
	public FinalReviewResultListResponse(FinalReviewResult finalReviewResult) {
		this(
			finalReviewResult.getId(),
			finalReviewResult.getTitle(),
			finalReviewResult.getCreateAt()
		);
	}
}
