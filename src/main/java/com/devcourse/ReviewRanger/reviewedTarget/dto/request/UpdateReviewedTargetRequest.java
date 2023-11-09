package com.devcourse.ReviewRanger.reviewedTarget.dto.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.devcourse.ReviewRanger.reply.dto.request.UpdateReplyRequest;
import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateReviewedTargetRequest(
	@NotNull(message = "수신자 Id는 Null값 일 수 없습니다.")
	Long receiverId,

	@JsonProperty("replies")
	List<UpdateReplyRequest> updateReplyRequests
) {
}
