package com.devcourse.ReviewRanger.openAI.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.devcourse.ReviewRanger.openAI.application.OpenAIService;
import com.devcourse.ReviewRanger.openAI.dto.request.CleanRepliesRequest;
import com.fasterxml.jackson.core.JsonProcessingException;

import reactor.core.publisher.Flux;

@RestController
public class OpenAIController {

	private final OpenAIService chatGptService;

	public OpenAIController(OpenAIService chatGptService) {
		this.chatGptService = chatGptService;
	}

	@PostMapping(value = "/replies/clean", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> cleanReplies(@RequestBody CleanRepliesRequest cleanRepliesRequest) {
		try {
			return chatGptService.cleanRepliesOrThrow(cleanRepliesRequest);
		} catch (JsonProcessingException je) {
			return Flux.empty();
		}
	}
}
