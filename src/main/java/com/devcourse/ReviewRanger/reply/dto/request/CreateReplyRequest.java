package com.devcourse.ReviewRanger.reply.dto.request;

import com.devcourse.ReviewRanger.reply.domain.Reply;

public record CreateReplyRequest(
	Long responserId,

	Long questionId,

	Long objectOptionId,

	String answerText
) {
	public Reply toEntity() {
		return new Reply(responserId, questionId, objectOptionId, answerText);
	}
}
