package com.devcourse.ReviewRanger.surveyresult.dto.response;

import java.time.LocalDateTime;

import com.devcourse.ReviewRanger.common.constant.Status;
import com.devcourse.ReviewRanger.surveyresult.domain.SurveyResult;

public record SurveyResultResponse(
	Long surveyId,
	Status status,
	LocalDateTime submitAt
) {

	public SurveyResultResponse(SurveyResult surveyResult) {
		this(
			surveyResult.getSurvey().getId(),
			surveyResult.getStatus(),
			surveyResult.getSubmitAt()
		);
	}
}
