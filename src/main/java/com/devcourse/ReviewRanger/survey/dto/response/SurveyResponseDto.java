package com.devcourse.ReviewRanger.survey.dto.response;

import java.time.LocalDateTime;

import com.devcourse.ReviewRanger.survey.domain.SurveyType;

public record SurveyResponseDto(
	Long surveyId,
	String title,
	String description,
	SurveyType surveyType,
	LocalDateTime closeAt,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {
	public SurveyResponseDto(
		Long surveyId,
		String title,
		SurveyType surveyType,
		LocalDateTime createdAt,
		LocalDateTime updatedAt) {
		this(
			surveyId,
			title,
			null,
			surveyType,
			null,
			createdAt,
			updatedAt
		);
	}
}
