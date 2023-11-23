package com.devcourse.ReviewRanger.openAI.dto.request;

import java.util.List;

public record CleanRepliesRequest(
	List<String> replies
) {

	public String generateReplies(){
		return String.join(", ", replies);
	}
}
