package com.devcourse.ReviewRanger.openAI.dto.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OpenAIRequest(
	String model,

	@JsonProperty("max_tokens")
	Integer maxTokens,

	Double temperature,

	Boolean stream,

	List<Message> messages
) {
}
