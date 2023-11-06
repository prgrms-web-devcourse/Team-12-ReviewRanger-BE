package com.devcourse.ReviewRanger.question.dto.request;

import java.util.ArrayList;
import java.util.List;

import com.devcourse.ReviewRanger.question.domain.Question;
import com.devcourse.ReviewRanger.question.domain.QuestionType;
import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateQuestionRequest(
	String title,
	QuestionType type,
	Integer sequence,
	Boolean isRequired,
	Boolean isDuplicated,
	@JsonProperty("questionOptions") List<CreateQuestionOptionRequest> createQuestionOptionRequests
) {
	public Question toEntity() {
		return new Question(title, type, sequence, isRequired, isDuplicated);
	}
}
