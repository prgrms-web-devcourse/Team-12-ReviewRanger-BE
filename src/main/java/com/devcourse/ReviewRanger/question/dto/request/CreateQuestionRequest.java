package com.devcourse.ReviewRanger.question.dto.request;

import java.util.List;

import com.devcourse.ReviewRanger.question.domain.Question;
import com.devcourse.ReviewRanger.question.domain.QuestionType;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "질문 생성 DTO")
public record CreateQuestionRequest(
	@Schema(description = "질문 제목")
	String title,

	@Schema(description = "질문 설명")
	String description,

	@Schema(description = "질문 타입")
	QuestionType type,

	@Schema(description = "질문 답변 작성 필수 여부")
	Boolean isRequired,

	@Schema(description = "질문 옵션 목록")
	@JsonProperty("questionOptions")
	List<CreateQuestionOptionRequest> createQuestionOptionRequests
) {
	public Question toEntity() {
		return new Question(this.title, this.description, this.type, this.isRequired);
	}
}
