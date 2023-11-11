package com.devcourse.ReviewRanger.finalReviewResult.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalQuestionType;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreateFinalReplyRequest(

	@Schema(name = "질문 제목")
	@NotBlank(message = "질문 제목은 빈값 일 수 없습니다.")
	String questionTitle,

	@Schema(name = "질문 타입")
	@NotBlank(message = "잘문 타입은 빈값 일 수 없습니다.")
	FinalQuestionType questionType,

	@Schema(name = "답변 리스트")
	@NotBlank(message = "답변은 빈값 일 수 없습니다.")
	List<Object> answers
) {
}
