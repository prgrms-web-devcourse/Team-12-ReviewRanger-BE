package com.devcourse.ReviewRanger.openAI.dto.response;

import java.util.List;

public record OpenAIResponse(
	List<Choice> choices
) {
	public String getAnswer(){
		return choices().get(0).message().content();
	}
}
