package com.devcourse.ReviewRanger.reviewedTarget.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.devcourse.ReviewRanger.reply.dto.response.ReplyResponse;
import com.devcourse.ReviewRanger.reviewedTarget.domain.ReviewedTarget;
import com.devcourse.ReviewRanger.user.dto.UserResponse;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "리뷰 타겟 기본 응답 DTO")
public record ReviewedTargetResponse(
	@Schema(description = "리뷰 타겟 Id")
	Long id,

	@Schema(description = "수신자")
	UserResponse receiver,

	@Schema(description = "응답자")
	UserResponse responser,

	@Schema(description = "참여 Id")
	Long participationId,

	@Schema(description = "답변들")
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
