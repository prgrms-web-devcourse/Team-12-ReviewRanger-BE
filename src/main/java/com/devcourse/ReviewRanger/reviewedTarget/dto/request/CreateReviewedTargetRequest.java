package com.devcourse.ReviewRanger.reviewedTarget.dto.request;

import java.util.List;

import com.devcourse.ReviewRanger.reply.dto.request.CreateReplyRequest;
import com.devcourse.ReviewRanger.reviewedTarget.domain.ReviewedTarget;

public record CreateReviewedTargetRequest(
	Long subjectId,

	List<CreateReplyRequest> responses
) {
	public ReviewedTarget toEntity() {
		return new ReviewedTarget(subjectId);
	}
}
