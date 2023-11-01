package com.devcourse.ReviewRanger.survey.dto;

import java.util.ArrayList;
import java.util.List;

import com.devcourse.ReviewRanger.question.domain.Question;
import com.devcourse.ReviewRanger.question.domain.QuestionOption;
import com.devcourse.ReviewRanger.question.domain.QuestionType;
import com.fasterxml.jackson.annotation.JsonProperty;

public record QuestionDto(
	String title,
	QuestionType type,
	@JsonProperty("questionOptions") List<QuestionOptionDto> questionOptionDtos,
	Integer sequence,
	Boolean isDuplicated,
	Boolean isRequired
) {

	public Question toEntity() {
		List<QuestionOption> questionOptions = isDuplicated
			? questionOptionDtos.stream().map(QuestionOptionDto::toEntity).toList()
			: new ArrayList<>();

		return new Question(title, type, sequence, isRequired, isDuplicated, questionOptions);
	}
}
