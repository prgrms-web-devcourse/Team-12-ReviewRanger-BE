package com.devcourse.ReviewRanger.response.dto.request;

import java.util.List;

public record CreateResponse(
	Long surveyId,

	Long responserId,

	List<Results> results
) {
}
