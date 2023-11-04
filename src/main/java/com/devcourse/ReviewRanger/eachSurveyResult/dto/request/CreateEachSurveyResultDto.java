package com.devcourse.ReviewRanger.eachSurveyResult.dto.request;

import java.util.List;

import com.devcourse.ReviewRanger.response.dto.request.CreateResponseDto;

public record CreateEachSurveyResultDto(
	Long reviewerId,

	List<CreateResponseDto> responses
) {
}
