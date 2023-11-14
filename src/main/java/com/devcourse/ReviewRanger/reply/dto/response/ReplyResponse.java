package com.devcourse.ReviewRanger.reply.dto.response;

import java.time.LocalDateTime;

import com.devcourse.ReviewRanger.question.dto.response.GetQuestionOptionResponse;
import com.devcourse.ReviewRanger.reply.domain.Reply;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "응답자의 모든 답변 내용 조회 기능 응답 DTO")
public record ReplyResponse(
	@Schema(description = "답변 Id")
	Long id,

	@Schema(description = "질문 Id")
	Long questionId,

	@Schema(description = "객관식, 드롭다운 용 답변 Id")
	GetQuestionOptionResponse questionOption,//QuestionOption

	@Schema(description = "주관식 답변")
	String answerText,

	@Schema(description = "답변자와 응답자 관계")
	Long reviewedTargetId,

	@Schema(description = "답변 생성일")
	LocalDateTime createdAt,

	@Schema(description = "답변 수정일")
	LocalDateTime updatedAt
) {
	public static ReplyResponse toResponse(Reply reply, GetQuestionOptionResponse questionOption) {
		return new ReplyResponse(
			reply.getId(),
			reply.getQuestionId(),
			questionOption,
			reply.getAnswerText(),
			reply.getReviewedTarget().getId(),
			reply.getCreateAt(),
			reply.getUpdatedAt()
		);
	}

	public static ReplyResponse toResponse(Reply reply) {
		return new ReplyResponse(
			reply.getId(),
			reply.getQuestionId(),
			null,
			reply.getAnswerText(),
			reply.getReviewedTarget().getId(),
			reply.getCreateAt(),
			reply.getUpdatedAt()
		);
	}
}
