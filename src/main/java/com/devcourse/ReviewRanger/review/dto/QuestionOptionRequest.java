package com.devcourse.ReviewRanger.review.dto;

import com.devcourse.ReviewRanger.question.domain.QuestionOption;

public record QuestionOptionRequest(String optionContext) {

	public QuestionOption toEntity() {
		return new QuestionOption(optionContext);
	}
}
