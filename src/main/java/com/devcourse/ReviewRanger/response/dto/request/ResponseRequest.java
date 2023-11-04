package com.devcourse.ReviewRanger.response.dto.request;

import com.devcourse.ReviewRanger.response.domain.Response;

public record ResponseRequest(
	Long responserId,
	String answerText
) {

	public Response toEntity() {
		return new Response(responserId, answerText);
	}
}
