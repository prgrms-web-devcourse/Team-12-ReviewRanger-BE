package com.devcourse.ReviewRanger.reply.dto.response;

import com.devcourse.ReviewRanger.reply.domain.Reply;
import com.devcourse.ReviewRanger.reviewedTarget.domain.ReviewedTarget;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "응답자의 모든 답변 내용 조회 기능 응답 DTO")
public record ReplyResponse(
	@Schema(description = "답변 Id")
	Long id,

	@Schema(description = "응답자 Id")
	Long responserId,

	@Schema(description = "질문 Id")
	Long questionId,

	@Schema(description = "객관식, 드롭다운 용 답변 Id")
	Long objectOptionId,

	@Schema(description = "주관식 답변")
	String answerText,

	@Schema(description = "답변자와 응답자 관계")
	ReviewedTarget reviewedTarget
) {
	public ReplyResponse(Reply reply) {
		this(
			reply.getId(),
			reply.getResponserId(),
			reply.getQuestionId(),
			reply.getObjectOptionId(),
			reply.getAnswerText(),
			null
		);
	}
}
