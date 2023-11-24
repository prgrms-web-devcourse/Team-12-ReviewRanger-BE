package com.devcourse.ReviewRanger.finalReviewResult.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "주관식 리뷰 결과 업데이트 요청 DTO")
public record UpdateAnswerOfSubject(
	@Schema(description = "수신자 id")
	@NotNull(message = "리뷰 대상 id는 빈값 일 수 없습니다.")
	Long userId,

	@Schema(description = "리뷰 id")
	@NotNull(message = "리뷰 id는 빈값 일 수 없습니다.")
	Long reviewId,

	@Schema(description = "질문 id")
	@NotNull(message = "질문 id는 빈값 일 수 없습니다.")
	Long questionId,

	@Schema(description = "정제된 주관식 답변 결과")
	@NotBlank(message = "정제된 주관식 답변 결과는 빈값 일 수 없습니다")
	String answer
) {
}
