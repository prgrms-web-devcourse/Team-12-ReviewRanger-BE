package com.devcourse.ReviewRanger.openAI;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.*;
import static com.devcourse.ReviewRanger.openAI.constant.Command.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.devcourse.ReviewRanger.common.exception.RangerException;
import com.devcourse.ReviewRanger.common.response.RangerResponse;
import com.devcourse.ReviewRanger.openAI.dto.request.ElementRequest;
import com.devcourse.ReviewRanger.openAI.dto.request.OpenAIRequest;
import com.devcourse.ReviewRanger.openAI.dto.response.OpenAIResponse;
import com.devcourse.ReviewRanger.openAI.util.PromptUtil;

@RestController
public class OpenAIController {

	@Qualifier("openaiRestTemplate")
	@Autowired
	private RestTemplate restTemplate;

	@Value("${openai.model}")
	private String model;

	@Value("${openai.api.url}")
	private String apiUrl;

	@Value("${openai.api.n}")
	private int n;

	@Value("${openai.api.temperature}")
	private double temperature;

	@PostMapping("/replies/clean")
	public RangerResponse<String> chat(@RequestBody ElementRequest elementRequest) {
		PromptUtil promptUtil = new PromptUtil(elementRequest.replies(), COMMAND_FOR_CLEAN_REPLIES);

		OpenAIRequest request = new OpenAIRequest(model, n, temperature, promptUtil.generateCommand());
		OpenAIResponse response = restTemplate.postForObject(apiUrl, request, OpenAIResponse.class);

		if (response == null || response.choices() == null || response.choices().isEmpty()) {
			new RangerException(NOT_RECEIVED_RESPONSE_FROM_OPEN_AI_API);
		}

		return RangerResponse.ok(response.getAnswer());
	}
}
