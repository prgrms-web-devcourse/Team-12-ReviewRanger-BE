package com.devcourse.ReviewRanger.openAI.dto.request;

import java.util.ArrayList;
import java.util.List;

import com.devcourse.ReviewRanger.openAI.dto.common.Message;

public record OpenAIRequest(
	String model,

	int n,

	double temperature,

	List<Message> messages
) {
	public OpenAIRequest(String model, int n, double temperature, String prompt) {
		this(
			model,
			n,
			temperature,
			new ArrayList<>(List.of(new Message("user", prompt)))
		);
	}
}
