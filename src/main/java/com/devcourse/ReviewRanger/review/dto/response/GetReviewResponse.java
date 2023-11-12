package com.devcourse.ReviewRanger.review.dto.response;

import java.time.LocalDateTime;

import com.devcourse.ReviewRanger.participation.domain.ReviewStatus;
import com.devcourse.ReviewRanger.review.domain.Review;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "리뷰 전체 조회 응답 DTO")
public record GetReviewResponse(
	@Schema(description = "리뷰 고유 id")
	Long reviewId,

	@Schema(description = "리뷰 제목")
	String title,

	@Schema(description = "리뷰 진행 상태")
	ReviewStatus status,

	@Schema(description = "리뷰 생성 일자")
	LocalDateTime createdAt,

	@Schema(description = "리뷰를 제출한 응답자 수")
	Long responserCount
) {
	public GetReviewResponse(Review review, Long responserCount) {
		this(
			review.getId(),
			review.getTitle(),
			review.getStatus(),
			review.getCreateAt(),
			responserCount
		);
	}
}
