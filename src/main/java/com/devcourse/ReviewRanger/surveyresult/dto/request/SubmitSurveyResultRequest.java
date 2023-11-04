package com.devcourse.ReviewRanger.surveyresult.dto.request;

import java.util.List;

import com.devcourse.ReviewRanger.eachSurveyResult.dto.request.EachSurveyRequest;

public record SubmitSurveyResultRequest(
	Long surveyResultId,
	List<EachSurveyRequest> eachSurveyRequests
) {
}
