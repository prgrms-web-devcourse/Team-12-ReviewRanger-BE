package com.devcourse.ReviewRanger.eachSurveyResult.dto.request;

import java.util.List;

import com.devcourse.ReviewRanger.response.dto.request.CreateResponseRequest;

public record CreateEachSurveyResultRequest(
	Long reviewerId,

	List<CreateResponseRequest> responses
) {
}
