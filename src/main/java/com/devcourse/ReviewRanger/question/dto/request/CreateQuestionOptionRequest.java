package com.devcourse.ReviewRanger.question.dto.request;

import com.devcourse.ReviewRanger.question.domain.QuestionOption;

public record CreateQuestionOptionRequest(
	Integer sequence,
	String optionContext
) {

	public QuestionOption toEntity() {
		return new QuestionOption(this.sequence, this.optionContext);
	}
}
