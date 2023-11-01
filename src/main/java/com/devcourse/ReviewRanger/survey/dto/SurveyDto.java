package com.devcourse.ReviewRanger.survey.dto;

import java.time.LocalDateTime;

import com.devcourse.ReviewRanger.survey.domain.Survey;
import com.devcourse.ReviewRanger.survey.domain.SurveyType;

public record SurveyDto(String title, String description, SurveyType surveyType, LocalDateTime closedAt) {

	public Survey toEntity() {
		return new Survey(title, description, surveyType, closedAt);
	}
}

