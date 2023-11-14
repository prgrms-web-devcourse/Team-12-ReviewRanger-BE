package com.devcourse.ReviewRanger.reviewedTarget.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.devcourse.ReviewRanger.reply.dto.response.ReplyResponse;
import com.devcourse.ReviewRanger.reviewedTarget.domain.ReviewedTarget;
import com.devcourse.ReviewRanger.user.dto.UserResponse;

public record ReviewedTargetResponse(
	Long id,

	UserResponse user, //receiver

	Long participationId,

	List<ReplyResponse> replies
) {
	public static ReviewedTargetResponse toResponse(ReviewedTarget reviewedTarget, UserResponse user) {
		return new ReviewedTargetResponse(
			reviewedTarget.getId(),
			user,
			reviewedTarget.getParticipationId(),
			new ArrayList<>()
		);
	}

	public void addRepliesResponse(List<ReplyResponse> replyResponses) {
		replies.addAll(replyResponses);
	}
}
