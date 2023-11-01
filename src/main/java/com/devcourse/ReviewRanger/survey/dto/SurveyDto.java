package com.devcourse.ReviewRanger.survey.dto;

import com.devcourse.ReviewRanger.survey.domain.Survey;
import com.devcourse.ReviewRanger.survey.domain.SurveyType;

public record SurveyDto(String title, String description, SurveyType surveyType) {

	public Survey toEntity() {
		return new Survey(title, description, surveyType);
	}
}

