package com.devcourse.ReviewRanger.survey.dto.request;

import java.util.List;

import com.devcourse.ReviewRanger.question.domain.Question;
import com.devcourse.ReviewRanger.survey.domain.Survey;
import com.devcourse.ReviewRanger.survey.dto.QuestionDto;
import com.devcourse.ReviewRanger.survey.dto.SurveyDto;
import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateSurveyRequest(
	@JsonProperty("survey") SurveyDto surveyDto,
	@JsonProperty("questions") List<QuestionDto> questionDtos
) {

	public Survey toSurvey() {
		return surveyDto.toEntity();
	}

	public List<Question> toQuestions() {
		return questionDtos.stream().map(questionDto -> questionDto.toEntity()).toList();
	}
}
