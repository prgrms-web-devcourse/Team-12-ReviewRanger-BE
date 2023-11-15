package com.devcourse.ReviewRanger.finalReviewResult.dto;

import java.util.List;

import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalQuestionType;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "최종 리뷰의 질문과 답변 조회 응답 DTO")
public record GetFinalReviewAnswerResponse(
	@Schema(description = "질문 id")
	Long questionId,

	@Schema(description = "질문 타입")
	FinalQuestionType finalQuestionType,

	@Schema(description = "질문 제목")
	String questionTitle,

	@Schema(description = "답변 id 목록")
	List<Long> answerIdList,

	@Schema(description = "답변 내용 목록")
	List<Object> answers
) {
}
