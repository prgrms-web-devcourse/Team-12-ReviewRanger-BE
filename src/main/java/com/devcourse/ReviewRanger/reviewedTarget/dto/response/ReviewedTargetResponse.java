package com.devcourse.ReviewRanger.reviewedTarget.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.devcourse.ReviewRanger.reply.dto.response.ReplyResponse;
import com.devcourse.ReviewRanger.reviewedTarget.domain.ReviewedTarget;
import com.devcourse.ReviewRanger.user.dto.UserResponse;

import io.swagger.v3.oas.annotations.media.Schema;

public record ReviewedTargetResponse(
	Long id,

	UserResponse receiver, //receiver

	UserResponse responser,

	Long participationId,

	List<ReplyResponse> replies,

	@Schema(description = "생성일")
	LocalDateTime createdAt,

	@Schema(description = "수정일")
	LocalDateTime updatedAt
) {
	public static ReviewedTargetResponse toResponse(ReviewedTarget reviewedTarget, UserResponse receiver,
		UserResponse responer) {
		return new ReviewedTargetResponse(
			reviewedTarget.getId(),
			receiver,
			responer,
			reviewedTarget.getParticipationId(),
			new ArrayList<>(),
			reviewedTarget.getCreateAt(),
			reviewedTarget.getUpdatedAt()
		);
	}

	public void addRepliesResponse(List<ReplyResponse> replyResponses) {
		replies.addAll(replyResponses);
	}
}
