package com.devcourse.ReviewRanger.participation.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResponserResponse(
	Long participationId,

	@JsonProperty("id")
	Long responserId,

	@JsonProperty("name")
	String responserName,

	LocalDateTime submitAt
) {
}
