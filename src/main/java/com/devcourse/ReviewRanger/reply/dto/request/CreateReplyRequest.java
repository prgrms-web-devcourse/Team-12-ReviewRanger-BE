package com.devcourse.ReviewRanger.reply.dto.request;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.devcourse.ReviewRanger.reply.domain.Reply;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "답변 생성 요청 DTO")
public record CreateReplyRequest(
	@Schema(description = "질문 Id")
	@NotNull(message = "질문 Id는 Null값 일 수 없습니다.")
	Long questionId,

	@Schema(description = "질문 필수 여부")
	@NotNull(message = "질문 필수 여부는 Null값 일 수 없습니다.")
	@JsonProperty("isRequired")
	Boolean isRequiredQuestion,

	@Schema(description = "객관식, 드롭다운 용 답변 Id")
	@JsonProperty("answerChoice")
	Long questionOptionId,

	@Schema(description = "주관식 답변")
	@Size(max = 500, message = "주관식 답변은 최대 500자까지 가능합니다.")
	String answerText,

	@Schema(description = "별점 답변")
	@JsonProperty("answerRating")
	@DecimalMax(value = "5.0", message = "별점은 1~5까지 값만 가능합니다.")
	@Positive(message = "값은 양수여야 합니다.")
	Double rating,

	@Schema(description = "육각스탯 답변")
	@JsonProperty("answerHexa")
	@Max(value = 10, message = "헥사 스탯은 1~10까지 값만 가능합니다.")
	@Positive(message = "값은 양수여야 합니다.")
	Integer hexastat
) {
	public Reply toEntity() {
		return new Reply(questionId, questionOptionId, answerText, rating, hexastat);
	}
}
