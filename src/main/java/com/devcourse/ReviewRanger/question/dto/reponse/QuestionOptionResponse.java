package com.devcourse.ReviewRanger.question.dto.reponse;

import com.devcourse.ReviewRanger.question.domain.QuestionOption;

public record QuestionOptionResponse(
	Long id,
	String optionContext
) {
	public QuestionOptionResponse(QuestionOption questionOption) {
		this(
			questionOption.getId(),
			questionOption.getOptionContext()
		);
	}
}
