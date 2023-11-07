package com.devcourse.ReviewRanger.reviewedTarget.dto.request;

import java.util.List;

import com.devcourse.ReviewRanger.reply.dto.request.UpdateReplyRequest;

public record UpdateReviewedTargetRequest(
	Long subjectId,

	List<UpdateReplyRequest> responses
) {
}
