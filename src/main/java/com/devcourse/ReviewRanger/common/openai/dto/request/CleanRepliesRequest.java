package com.devcourse.ReviewRanger.common.openai.dto.request;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "주관식 답변 리스트 DTO")
public record CleanRepliesRequest(
	@Schema(description = "주관식 답변 리스트")
	List<String> replies
) {

	public String generateReplies() {
		return String.join(", ", replies);
	}
}
