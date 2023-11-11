package com.devcourse.ReviewRanger.participation.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "설문에 참여한 모든 응답자 조회 응답 DTO")
public record ResponserResponse(
	@Schema(description = "참여 Id")
	Long participationId,

	@Schema(description = "응답자 Id")
	@JsonProperty("id")
	Long responserId,

	@Schema(description = "응답자 이름")
	@JsonProperty("name")
	String responserName,

	@Schema(description = "답변 제출일")
	LocalDateTime submitAt
) {
}
