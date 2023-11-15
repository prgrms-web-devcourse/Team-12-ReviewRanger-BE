package com.devcourse.ReviewRanger.reviewedTarget.dto.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.devcourse.ReviewRanger.reply.dto.request.CreateReplyRequest;
import com.devcourse.ReviewRanger.reviewedTarget.domain.ReviewedTarget;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "리뷰 답변 요청 DTO")
public record CreateReviewedTargetRequest(
	@Schema(description = "수신자 Id")
	@NotNull(message = "수신자 Id는 Null값 일 수 없습니다.")
	Long receiverId,

	@Schema(description = "응답자 Id")
	@NotNull(message = "응답자 Id는 Null값 일 수 없습니다.")
	Long responserId,

	@Schema(description = "리뷰 답변 요청 DTO")
	@JsonProperty("replies")
	List<CreateReplyRequest> createReplyRequests
) {
	public ReviewedTarget toEntity() {
		return new ReviewedTarget(receiverId, responserId);
	}
}
