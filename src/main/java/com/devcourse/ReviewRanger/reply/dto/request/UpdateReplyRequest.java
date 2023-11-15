package com.devcourse.ReviewRanger.reply.dto.request;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "답변 수정 요청 DTO")
public record UpdateReplyRequest(
	@Schema(description = "답변 Id")
	@NotNull(message = "답변 Id는 Null값 일 수 없습니다.")
	Long id,

	@Schema(description = "질문 Id")
	@NotNull(message = "질문 Id는 Null값 일 수 없습니다.")
	Long questionId,

	@Schema(description = "객관식, 드롭다운 용 답변 Id")
	@JsonProperty("answerChoice")
	Long questionOptionId,

	@Schema(description = "주관식 답변")
	String answerText,

	@Schema(description = "별점 답변")
	@JsonProperty("answerRating")
	Double rating,

	@Schema(description = "육각스탯 답변")
	@JsonProperty("answerHexa")
	Integer hexastat
) {
}
