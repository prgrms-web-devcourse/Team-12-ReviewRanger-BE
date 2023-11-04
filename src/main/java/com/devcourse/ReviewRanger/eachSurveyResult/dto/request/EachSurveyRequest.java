package com.devcourse.ReviewRanger.eachSurveyResult.dto.request;

import java.util.List;

import com.devcourse.ReviewRanger.eachSurveyResult.domain.EachSurveyResult;

public record EachSurveyRequest(
	Long subjectId,
	List<QuestionResponseRequest> questionResponseRequests
) {
	public EachSurveyResult toEntity() {
		return new EachSurveyResult(subjectId);
	}
}
