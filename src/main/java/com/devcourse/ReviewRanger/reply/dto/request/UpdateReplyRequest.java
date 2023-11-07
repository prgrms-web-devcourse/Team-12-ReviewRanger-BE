package com.devcourse.ReviewRanger.reply.dto.request;

public record UpdateReplyRequest(
	Long id,

	Long responserId,

	Long questionId,

	Long objectOptionId,

	String answerText
) {
}
