package com.devcourse.ReviewRanger.reply.dto.request;

import javax.validation.constraints.NotNull;

public record UpdateReplyRequest(
	@NotNull(message = "답변 Id는 Null값 일 수 없습니다.")
	Long id,

	@NotNull(message = "응답자 Id는 Null값 일 수 없습니다.")
	Long responserId,

	@NotNull(message = "질문 Id는 Null값 일 수 없습니다.")
	Long questionId,

	Long objectOptionId,

	String answerText
) {
}
