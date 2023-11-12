package com.devcourse.ReviewRanger.question.dto.response;

import com.devcourse.ReviewRanger.question.domain.QuestionOption;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "질문 옵션 조회 응답 DTO")
public record GetQuestionOptionResponse(
	@Schema(description = "질문 옵션 고유 id")
	Long optionId,

	@Schema(description = "질문 옵션 제목")
	String optionName
) {
	public GetQuestionOptionResponse(QuestionOption questionOption) {
		this(
			questionOption.getId(),
			questionOption.getOptionName()
		);
	}
}
