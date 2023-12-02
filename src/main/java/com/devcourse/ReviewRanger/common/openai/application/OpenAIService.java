package com.devcourse.ReviewRanger.common.openai.application;

import static com.devcourse.ReviewRanger.common.config.OpenAIConfig.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.devcourse.ReviewRanger.common.config.OpenAIConfig;

import com.devcourse.ReviewRanger.common.openai.Prompt;
import com.devcourse.ReviewRanger.common.openai.constant.Command;
import com.devcourse.ReviewRanger.common.openai.dto.request.CleanRepliesRequest;
import com.devcourse.ReviewRanger.common.openai.dto.request.OpenAIRequest;
import com.devcourse.ReviewRanger.common.openai.dto.request.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;

@Service
public class OpenAIService {

	@Value("${openai.api.key}")
	private String openaiApiKey;

	private final ObjectMapper objectMapper = new ObjectMapper();

	public Flux<String> cleanRepliesOrThrow(CleanRepliesRequest cleanRepliesRequest) throws JsonProcessingException {
		WebClient client = WebClient.builder()
			.baseUrl(CHAT_URL)
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.defaultHeader(OpenAIConfig.AUTHORIZATION, OpenAIConfig.BEARER + openaiApiKey)
			.build();

		Prompt prompt = new Prompt(cleanRepliesRequest.generateReplies(), Command.COMMAND_FOR_CLEAN_REPLIES);
		Message message = new Message(OpenAIConfig.ROLE, prompt.generatePrompt());
		List<Message> messages = List.of(message);

		OpenAIRequest openAIRequest = new OpenAIRequest(
			OpenAIConfig.CHAT_MODEL,
			OpenAIConfig.MAX_TOKEN,
			OpenAIConfig.TEMPERATURE,
			OpenAIConfig.STREAM,
			messages
		);

		String requestValue = objectMapper.writeValueAsString(openAIRequest);

		Flux<String> eventStream = client.post()
			.bodyValue(requestValue)
			.accept(MediaType.TEXT_EVENT_STREAM)
			.retrieve()
			.bodyToFlux(String.class);

		return eventStream;
	}
}
