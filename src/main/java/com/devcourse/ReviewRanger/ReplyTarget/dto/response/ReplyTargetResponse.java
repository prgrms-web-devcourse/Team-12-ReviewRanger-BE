package com.devcourse.ReviewRanger.ReplyTarget.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.devcourse.ReviewRanger.ReplyTarget.domain.ReplyTarget;
import com.devcourse.ReviewRanger.reply.dto.response.ReplyResponse;
import com.devcourse.ReviewRanger.user.dto.UserResponse;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "답변 타겟 기본 응답 DTO")
public record ReplyTargetResponse(
	@Schema(description = "답변 타겟 Id")
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
	public static ReplyTargetResponse toResponse(ReplyTarget replyTarget, UserResponse receiver,
		UserResponse responer) {
		return new ReplyTargetResponse(
			replyTarget.getId(),
			receiver,
			responer,
			replyTarget.getParticipationId(),
			new ArrayList<>(),
			replyTarget.getCreateAt(),
			replyTarget.getUpdatedAt()
		);
	}

	public void addRepliesResponse(List<ReplyResponse> replyResponses) {
		replies.addAll(replyResponses);
	}
}
