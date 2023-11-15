package com.devcourse.ReviewRanger.participation.dto.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.devcourse.ReviewRanger.ReplyTarget.dto.request.CreateReplyTargetRequest;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "리뷰 답변 요청 DTO")
public record SubmitParticipationRequest(
	@Schema(description = "참여 Id")
	@NotNull(message = "참여 Id는 Null값 일 수 없습니다.")
	Long participationId,

	@Schema(description = "리뷰 답변 요청 DTO")
	@JsonProperty("reviewTargets")
	List<CreateReplyTargetRequest> createReplyTargetRequests
) {
}
