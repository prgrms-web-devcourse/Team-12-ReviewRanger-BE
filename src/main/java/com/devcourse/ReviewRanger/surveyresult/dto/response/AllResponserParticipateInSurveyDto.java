package com.devcourse.ReviewRanger.surveyresult.dto.response;

import java.util.List;

import com.devcourse.ReviewRanger.survey.dto.response.SurveyResponseDto;

public record AllResponserParticipateInSurveyDto(
	int responserCount,

	SurveyResponseDto surveyResponseDto,

	List<Responsers> responsers
) {
}
