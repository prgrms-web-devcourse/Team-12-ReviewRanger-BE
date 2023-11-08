package com.devcourse.ReviewRanger.reply.dto.response;

import com.devcourse.ReviewRanger.reply.domain.Reply;
import com.devcourse.ReviewRanger.reviewedTarget.domain.ReviewedTarget;

public record ReplyResponse(
	Long id,

	Long responerId,

	Long questionId,

	Long objectOptionId,

	String answerText,

	ReviewedTarget reviewedTarget
) {
	public ReplyResponse(Reply reply) {
		this(
			reply.getId(),
			reply.getResponserId(),
			reply.getQuestionId(),
			reply.getObjectOptionId(),
			reply.getAnswerText(),
			reply.getReviewedTarget()
		);
	}
}
