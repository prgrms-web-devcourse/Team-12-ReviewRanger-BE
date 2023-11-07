package com.devcourse.ReviewRanger.question.dto.response;

import com.devcourse.ReviewRanger.question.domain.QuestionOption;

public record GetQuestionOptionResponse(
	Integer sequence,
	String optionContext
) {
	public GetQuestionOptionResponse(QuestionOption questionOption) {
		this(
			questionOption.getSequence(),
			questionOption.getOptionContext()
		);
	}
}
