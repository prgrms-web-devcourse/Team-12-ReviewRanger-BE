package com.devcourse.ReviewRanger.finalReviewResult.dto;

import static com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResult.*;

import java.time.LocalDateTime;

import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResult;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "질답을 제외한 최종 리뷰 정보 조회 응답 DTO")
public record GetFinalReviewResultResponse(
	@Schema(description = "리뷰 대상 id")
	Long userId,

	@Schema(description = "리뷰 대상 이름")
	String userName,

	@Schema(description = "리뷰 id")
	Long reviewId,

	@Schema(description = "리뷰 제목")
	String title,

	@Schema(description = "리뷰 설명")
	String description,

	@Schema(description = "최종 리뷰 상태")
	Status status,

	@Schema(description = "최종 리뷰 생성 시간")
	LocalDateTime createdAt,

	@Schema(description = "최종 리뷰 수정 시간")
	LocalDateTime updatedAt
) {
	public GetFinalReviewResultResponse(FinalReviewResult entity) {
		this(
			entity.getUserId(),
			entity.getUserName(),
			entity.getReviewId(),
			entity.getTitle(),
			entity.getDescription(),
			entity.getStatus(),
			entity.getCreateAt(),
			entity.getUpdatedAt()
		);
	}
}
