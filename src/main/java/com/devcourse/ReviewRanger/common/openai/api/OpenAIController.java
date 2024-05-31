package com.devcourse.ReviewRanger.common.openai.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.devcourse.ReviewRanger.common.openai.application.OpenAIService;
import com.devcourse.ReviewRanger.common.openai.dto.request.CleanRepliesRequest;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;

@RestController
@Tag(name = "open AI", description = "open AI API")
public class OpenAIController {

	private final OpenAIService chatGptService;

	public OpenAIController(OpenAIService chatGptService) {
		this.chatGptService = chatGptService;
	}

	@Tag(name = "open AI")
	@Operation(summary = "답변 정제", description = "리뷰 주관식 답변 정제 API", responses = {
		@ApiResponse(responseCode = "200", description = "스트림 데이터 전송 성공")
	})
	@PostMapping(value = "/replies/clean", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> cleanReplies(@RequestBody CleanRepliesRequest cleanRepliesRequest) {
		try {
			return chatGptService.cleanRepliesOrThrow(cleanRepliesRequest);
		} catch (JsonProcessingException je) {
			return Flux.empty();
		}
	}
}
