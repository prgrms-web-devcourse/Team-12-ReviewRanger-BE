package com.devcourse.ReviewRanger.eachSurveyResult.dto.request;

import java.util.List;

import com.devcourse.ReviewRanger.response.dto.request.ResponseRequest;

public record QuestionResponseRequest(
	Long questionId,
	List<ResponseRequest> responseRequests
) {
}
