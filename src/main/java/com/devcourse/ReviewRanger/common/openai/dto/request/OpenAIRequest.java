package com.devcourse.ReviewRanger.common.openai.dto.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "chat completion 방식의 GTP 요청 DTO")
public record OpenAIRequest(
	@Schema(description = "text generation 모델명 ex)GPT 3.5")
	String model,

	@Schema(description = "최대 토큰 수")
	@JsonProperty("max_tokens")
	Integer maxTokens,

	@Schema(description = "유사한 질문에 대한 랜덤 정도")
	Double temperature,

	@Schema(description = "스트림 방식 수행 여부")
	Boolean stream,

	@Schema(description = "요청에 사용할 프롬프트")
	List<Message> messages
) {
}
