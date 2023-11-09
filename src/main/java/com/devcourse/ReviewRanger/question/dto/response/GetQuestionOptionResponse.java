package com.devcourse.ReviewRanger.question.dto.response;

import com.devcourse.ReviewRanger.question.domain.QuestionOption;

public record GetQuestionOptionResponse(
	Long optionId,
	String optionName
) {
	public GetQuestionOptionResponse(QuestionOption questionOption) {
		this(
			questionOption.getId(),
			questionOption.getOptionName()
		);
	}
}
