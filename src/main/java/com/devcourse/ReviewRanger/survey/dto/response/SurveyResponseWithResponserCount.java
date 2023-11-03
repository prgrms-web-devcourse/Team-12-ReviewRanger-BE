package com.devcourse.ReviewRanger.survey.dto.response;

import java.time.LocalDateTime;

import com.devcourse.ReviewRanger.survey.domain.Survey;
import com.devcourse.ReviewRanger.survey.domain.SurveyType;
import com.devcourse.ReviewRanger.common.constant.Status;

public record SurveyResponseWithResponserCount(
	Long surveyId,
	String title,
	String description,
	Status deadlineStatus,
	SurveyType surveyType,
	LocalDateTime createdAt,
	Long responserCount
) {
	public SurveyResponseWithResponserCount(Survey survey, Long responserCount) {
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
