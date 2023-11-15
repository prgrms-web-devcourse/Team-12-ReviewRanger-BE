package com.devcourse.ReviewRanger.participation.dto.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.devcourse.ReviewRanger.ReplyTarget.dto.request.CreateReplyTargetRequest;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "답변 생성 요청 DTO")
public record SubmitParticipationRequest(
	@Schema(description = "참여 Id")
	@NotNull(message = "참여 Id는 Null값 일 수 없습니다.")
	@JsonProperty("id")
	Long participationId,

	@Schema(description = "답변 타겟 생성 요청 DTO")
	@JsonProperty("replyTargets")
	List<CreateReplyTargetRequest> createReplyTargetRequests
) {
}
