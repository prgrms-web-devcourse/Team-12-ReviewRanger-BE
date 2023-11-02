package com.devcourse.ReviewRanger.surveyresult.dto.response;

import java.time.LocalDateTime;

public record Responsers(
	Long surveyResultId,

	Long id, //responserId

	String name, //responserName

	LocalDateTime updatedAt
) {
}
