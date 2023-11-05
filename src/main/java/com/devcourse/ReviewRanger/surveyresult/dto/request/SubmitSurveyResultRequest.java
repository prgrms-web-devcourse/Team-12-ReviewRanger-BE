package com.devcourse.ReviewRanger.surveyresult.dto.request;

import java.util.List;

import com.devcourse.ReviewRanger.eachSurveyResult.dto.request.CreateEachSurveyResultRequest;

public record SubmitSurveyResultRequest(
	Long surveyResultId,

	List<CreateEachSurveyResultRequest> eachSurveyResultDto
) {
}
