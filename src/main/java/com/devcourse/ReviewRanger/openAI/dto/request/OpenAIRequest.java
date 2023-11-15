package com.devcourse.ReviewRanger.openAI.dto.request;

import java.util.List;

import com.devcourse.ReviewRanger.openAI.dto.common.Message;

public record OpenAIRequest(
	String model,

	int n,

	double temperature,

	List<Message> messages
) {
}
