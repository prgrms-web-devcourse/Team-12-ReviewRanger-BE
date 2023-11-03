package com.devcourse.ReviewRanger.question.dto.reponse;

import java.util.List;

import com.devcourse.ReviewRanger.question.domain.Question;

import com.devcourse.ReviewRanger.question.domain.QuestionType;

public record QuestionResponse(
	Long id,
	String title,
	QuestionType type,
	Integer sequence,
	Boolean isRequired,
	Boolean isDuplicated,
	List<QuestionOptionResponse> questionOptionResponses
) {
	public QuestionResponse(Question question) {
		this(
			question.getId(),
			question.getTitle(),
			question.getType(),
			question.getSequence(),
			question.getIsRequired(),
			question.getIsDuplicated(),
			question.getQuestionOptions()
				.stream()
				.map(questionOption -> new QuestionOptionResponse(questionOption))
				.toList()
		);
	}
}
