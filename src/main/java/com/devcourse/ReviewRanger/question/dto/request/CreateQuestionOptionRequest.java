package com.devcourse.ReviewRanger.question.dto.request;

import com.devcourse.ReviewRanger.question.domain.QuestionOption;

import io.swagger.v3.oas.annotations.media.Schema;
@Schema(description = "질문 옵션 생성 DTO")
public record CreateQuestionOptionRequest(
	@Schema(description = "질문 옵션명")
	String optionName
) {

	public QuestionOption toEntity() {
		return new QuestionOption(this.optionName);
	}
}
