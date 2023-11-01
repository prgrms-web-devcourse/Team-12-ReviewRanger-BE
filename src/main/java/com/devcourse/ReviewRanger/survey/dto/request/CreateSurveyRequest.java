package com.devcourse.ReviewRanger.survey.dto.request;

import java.util.List;

import com.devcourse.ReviewRanger.question.domain.Question;
import com.devcourse.ReviewRanger.survey.domain.Survey;
import com.devcourse.ReviewRanger.survey.dto.QuestionDto;
import com.devcourse.ReviewRanger.survey.dto.SurveyDto;
import com.devcourse.ReviewRanger.surveyresult.domain.SurveyResult;
import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateSurveyRequest(
	@JsonProperty("survey") SurveyDto surveyDto,
	@JsonProperty("questions") List<QuestionDto> questionDtos,
	@JsonProperty("responserIdList") List<Long> responserIds

) {

	public Survey toSurvey() {
		return surveyDto.toEntity();
	}

	public List<Question> toQuestions() {
		return questionDtos.stream().map(questionDto -> questionDto.toEntity()).toList();
	}

	public List<SurveyResult> toSurveyResult() {
		return responserIds.stream().map(responserId -> new SurveyResult(responserId)).toList();
	}

}
