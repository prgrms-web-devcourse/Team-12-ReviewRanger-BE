package com.devcourse.ReviewRanger.question.dto.response;

import java.util.List;

import com.devcourse.ReviewRanger.question.domain.Question;
import com.devcourse.ReviewRanger.question.domain.QuestionType;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "질문 조회 응답 DTO")
public record GetQuestionResponse(
	@Schema(description = "질문 고유 id")
	Long id,

	@Schema(description = "질문 제목")
	String title,

	@Schema(description = "질문 설명")
	String description,

	@Schema(description = "질문 타입")
	QuestionType type,

	@Schema(description = "질문 필수 응답 여부")
	Boolean isRequired,

	@Schema(description = "질문 옵션 목록")
	List<GetQuestionOptionResponse> questionOptions
) {
	public GetQuestionResponse(Question question, List<GetQuestionOptionResponse> options) {
		this(
			question.getId(),
			question.getTitle(),
			question.getDescription(),
			question.getType(),
			question.getIsRequired(),
			options
		);
	}
}
