package com.devcourse.ReviewRanger.reply.dto.request;

import javax.validation.constraints.NotNull;

import com.devcourse.ReviewRanger.reply.domain.Reply;

public record CreateReplyRequest(
	@NotNull(message = "응답자 Id는 Null값 일 수 없습니다.")
	Long responserId,

	@NotNull(message = "질문 Id는 Null값 일 수 없습니다.")
	Long questionId,

	Long objectOptionId,

	String answerText
) {
	public Reply toEntity() {
		return new Reply(responserId, questionId, objectOptionId, answerText);
	}
}
