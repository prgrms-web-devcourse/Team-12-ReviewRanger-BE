package com.devcourse.ReviewRanger.question.dto.response;

import java.time.LocalDateTime;

import com.devcourse.ReviewRanger.question.domain.QuestionOption;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "질문 옵션 조회 응답 DTO")
public record GetQuestionOptionResponse(
	@Schema(description = "질문 옵션 고유 id")
	Long optionId,

	@Schema(description = "질문 옵션 제목")
	String optionName,

	@Schema(description = "생성일")
	LocalDateTime createdAt,

	@Schema(description = "수정일")
	LocalDateTime updatedAt
) {
	public GetQuestionOptionResponse(QuestionOption questionOption) {
		this(
			questionOption.getId(),
			questionOption.getOptionName(),
			questionOption.getCreateAt(),
			questionOption.getUpdatedAt()
		);
	}

	public static GetQuestionOptionResponse toResponse(QuestionOption questionOption) {
		return new GetQuestionOptionResponse(
			questionOption.getId(),
			questionOption.getOptionName(),
			questionOption.getCreateAt(),
			questionOption.getUpdatedAt()
		);
	}
}
