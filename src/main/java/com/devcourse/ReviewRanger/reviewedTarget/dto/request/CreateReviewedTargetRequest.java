package com.devcourse.ReviewRanger.reviewedTarget.dto.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.devcourse.ReviewRanger.reply.dto.request.CreateReplyRequest;
import com.devcourse.ReviewRanger.reviewedTarget.domain.ReviewedTarget;
import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateReviewedTargetRequest(
	@NotNull(message = "수신자 Id는 Null값 일 수 없습니다.")
	Long receiverId,

	@JsonProperty("replies")
	List<CreateReplyRequest> createReplyRequests
) {
	public ReviewedTarget toEntity() {
		return new ReviewedTarget(receiverId);
	}
}
