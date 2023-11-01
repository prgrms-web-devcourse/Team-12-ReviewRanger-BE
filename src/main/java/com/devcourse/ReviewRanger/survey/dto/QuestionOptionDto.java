package com.devcourse.ReviewRanger.survey.dto;

import com.devcourse.ReviewRanger.question.domain.QuestionOption;

public record QuestionOptionDto(String optionContext) {

	public QuestionOption toEntity() {
		return new QuestionOption(optionContext);
	}
}
