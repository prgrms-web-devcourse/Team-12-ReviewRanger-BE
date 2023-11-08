package com.devcourse.ReviewRanger.reply.dto.request;

import javax.validation.constraints.NotBlank;

public record UpdateReplyRequest(
	@NotBlank(message = "답변 Id는 빈값 일 수 없습니다.")
	Long id,

	@NotBlank(message = "응답자 Id는 빈값 일 수 없습니다.")
	Long responserId,

	@NotBlank(message = "질문 Id는 빈값 일 수 없습니다.")
	Long questionId,

	Long objectOptionId,

	String answerText
) {
}
