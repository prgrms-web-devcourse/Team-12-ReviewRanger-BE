package com.devcourse.ReviewRanger.survey.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.devcourse.ReviewRanger.question.dto.reponse.QuestionResponse;
import com.devcourse.ReviewRanger.survey.domain.Survey;
import com.devcourse.ReviewRanger.survey.domain.SurveyType;
import com.devcourse.ReviewRanger.surveyresult.domain.DeadlineStatus;

public record SurveyResponse(
	Long surveyId,
	String title,
	String description,
	DeadlineStatus deadlineStatus,
	SurveyType surveyType,
	LocalDateTime createdAt,
	List<QuestionResponse> questionResponse
) {
	public SurveyResponse(Survey survey) {
		this(
			survey.getId(),
			survey.getTitle(),
			survey.getDescription(),
			survey.getStatus(),
			survey.getType(),
			survey.getCreateAt(),
			survey.getQuestions().stream().map(question -> new QuestionResponse(question))
				.toList()
		);
	}
}
