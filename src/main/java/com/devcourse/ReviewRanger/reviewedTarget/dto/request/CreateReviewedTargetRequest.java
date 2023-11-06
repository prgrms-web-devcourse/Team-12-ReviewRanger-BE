package com.devcourse.ReviewRanger.reviewedTarget.dto.request;

import java.util.List;

import com.devcourse.ReviewRanger.reply.dto.request.CreateReplyRequest;

public record CreateReviewedTargetRequest(
	Long subjectId,

	List<CreateReplyRequest> responses
) {
}
