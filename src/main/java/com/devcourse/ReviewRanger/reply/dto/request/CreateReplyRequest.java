package com.devcourse.ReviewRanger.reply.dto.request;

import javax.validation.constraints.NotNull;

import com.devcourse.ReviewRanger.reply.domain.Reply;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "답변 생성 요청 DTO")
public record CreateReplyRequest(
	@Schema(description = "질문 Id")
	@NotNull(message = "질문 Id는 Null값 일 수 없습니다.")
	Long questionId,

	@Schema(description = "객관식, 드롭다운 용 답변 Id")
	@JsonProperty("answerChoice")
	Long questionOptionId,

	@Schema(description = "주관식 답변")
	String answerText
) {
	public Reply toEntity() {
		return new Reply(questionId, questionOptionId, answerText);
	}
}
