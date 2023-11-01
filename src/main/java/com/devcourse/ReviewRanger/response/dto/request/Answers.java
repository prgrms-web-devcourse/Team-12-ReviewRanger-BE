package com.devcourse.ReviewRanger.response.dto.request;

import java.util.List;

public record Answers(
	Long questionId,

	String questionType,

	List<String> answer
) {
}
