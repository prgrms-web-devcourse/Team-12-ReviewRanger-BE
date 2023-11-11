package com.devcourse.ReviewRanger.review.dto.response;

import java.util.List;

import com.devcourse.ReviewRanger.participation.domain.DeadlineStatus;

import com.devcourse.ReviewRanger.question.dto.response.GetQuestionResponse;
import com.devcourse.ReviewRanger.review.domain.Review;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "리뷰 상세 조회 응답 DTO")
public record GetReviewDetailResponse(
	@Schema(description = "리뷰 고유 id")
	Long id,

	@Schema(description = "리뷰 제목")
	String title,

	@Schema(description = "리뷰 진행 상태")
	DeadlineStatus status,

	@Schema(description = "리뷰에 포함된 질문 목록")
	List<GetQuestionResponse> questions
) {
	public GetReviewDetailResponse(Review review, List<GetQuestionResponse> questions) {
		this(
			review.getId(),
			review.getTitle(),
			review.getStatus(),
			questions
		);
	}
}
