package com.devcourse.ReviewRanger.survey.dto.response;

import java.time.LocalDateTime;

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
	Long responserCount
) {
	public SurveyResponse(Survey survey, Long responserCount) {
		this(
			survey.getId(),
			survey.getTitle(),
			survey.getDescription(),
			survey.getStatus(),
			survey.getType(),
			survey.getCreateAt(),
			responserCount
		);
	}
}
