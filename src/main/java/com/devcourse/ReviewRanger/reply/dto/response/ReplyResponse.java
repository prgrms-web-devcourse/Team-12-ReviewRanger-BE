package com.devcourse.ReviewRanger.reply.dto.response;

import java.time.LocalDateTime;

import com.devcourse.ReviewRanger.question.dto.response.GetQuestionOptionResponse;
import com.devcourse.ReviewRanger.reply.domain.Reply;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "답변 기본 응답 DTO")
public record ReplyResponse(
	@Schema(description = "답변 Id")
	Long id,

	@Schema(description = "질문 Id")
	Long questionId,

	@Schema(description = "객관식, 드롭다운, 육각스탯 용 옵션")
	GetQuestionOptionResponse questionOption,

	@Schema(description = "주관식 답변")
	String answerText,

	@Schema(description = "리뷰 타겟")
	Long reviewedTargetId,

	@Schema(description = "생성일")
	LocalDateTime createdAt,

	@Schema(description = "수정일")
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
