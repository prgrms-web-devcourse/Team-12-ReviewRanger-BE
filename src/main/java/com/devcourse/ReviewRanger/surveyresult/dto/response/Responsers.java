package com.devcourse.ReviewRanger.surveyresult.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Responsers(
	Long surveyResultId,

	@JsonProperty("id")
	Long responserId,

	@JsonProperty("name")
	String responserName,

	LocalDateTime updatedAt
) {
}
