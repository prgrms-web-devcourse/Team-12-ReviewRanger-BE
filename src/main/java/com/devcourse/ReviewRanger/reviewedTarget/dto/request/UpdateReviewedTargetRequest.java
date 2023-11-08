package com.devcourse.ReviewRanger.reviewedTarget.dto.request;

import java.util.List;

import com.devcourse.ReviewRanger.reply.dto.request.UpdateReplyRequest;
import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateReviewedTargetRequest(
	Long subjectId,

	@JsonProperty("replies")
	List<UpdateReplyRequest> updateReplyRequests
) {
}
