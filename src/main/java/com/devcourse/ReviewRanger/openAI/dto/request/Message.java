package com.devcourse.ReviewRanger.openAI.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "chat completion 방식의 GTP prompt DTO")
public record Message(
	@Schema(description = "요청자 역할")
	String role,

	@Schema(description = "프롬프트")
	String content
) {
}
