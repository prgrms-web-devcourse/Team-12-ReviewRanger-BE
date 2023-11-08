package com.devcourse.ReviewRanger.question.dto.request;

import com.devcourse.ReviewRanger.question.domain.QuestionOption;

public record CreateQuestionOptionRequest(
	String optionName
) {

	public QuestionOption toEntity() {
		return new QuestionOption(this.optionName);
	}
}
