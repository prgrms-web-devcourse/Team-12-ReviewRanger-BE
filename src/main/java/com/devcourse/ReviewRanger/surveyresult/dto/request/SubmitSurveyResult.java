package com.devcourse.ReviewRanger.surveyresult.dto.request;

import java.util.List;

import com.devcourse.ReviewRanger.eachSurveyResult.dto.request.CreateEachSurveyResultDto;

public record SubmitSurveyResult(
	Long surveyResultId,

	List<CreateEachSurveyResultDto> eachSurveyResultDto
) {
}
