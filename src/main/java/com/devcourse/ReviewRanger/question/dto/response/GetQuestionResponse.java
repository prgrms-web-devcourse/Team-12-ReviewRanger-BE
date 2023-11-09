package com.devcourse.ReviewRanger.question.dto.response;

import java.util.List;

import com.devcourse.ReviewRanger.question.domain.Question;
import com.devcourse.ReviewRanger.question.domain.QuestionType;

public record GetQuestionResponse(
	Long id,
	String title,
	QuestionType type,
	Boolean isRequired,
	List<GetQuestionOptionResponse> questionOptions
) {
	public GetQuestionResponse(Question question, List<GetQuestionOptionResponse> options) {
		this(
			question.getId(),
			question.getTitle(),
			question.getType(),
			question.getIsRequired(),
			options
		);
	}
}
