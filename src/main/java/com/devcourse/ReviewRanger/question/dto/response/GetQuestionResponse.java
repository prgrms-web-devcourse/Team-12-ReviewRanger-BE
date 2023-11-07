package com.devcourse.ReviewRanger.question.dto.response;

import java.util.List;

import com.devcourse.ReviewRanger.question.domain.Question;
import com.devcourse.ReviewRanger.question.domain.QuestionType;

public record GetQuestionResponse(
	String title,
	QuestionType type,
	Integer sequence,
	Boolean isRequired,
	Boolean isDuplicated,
	List<GetQuestionOptionResponse> options
) {
	public GetQuestionResponse(Question question, List<GetQuestionOptionResponse> options) {
		this(
			question.getTitle(),
			question.getType(),
			question.getSequence(),
			question.getIsRequired(),
			question.getIsDuplicated(),
			options
		);
	}
}
