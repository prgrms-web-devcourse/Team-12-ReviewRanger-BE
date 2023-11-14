package com.devcourse.ReviewRanger.openAI.dto.response;

import java.util.List;

public record OpenAIResponse(
	List<Choice> choices
) {
}
