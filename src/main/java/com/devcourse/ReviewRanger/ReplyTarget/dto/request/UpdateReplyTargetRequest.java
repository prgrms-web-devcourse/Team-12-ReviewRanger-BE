package com.devcourse.ReviewRanger.ReplyTarget.dto.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.devcourse.ReviewRanger.reply.dto.request.UpdateReplyRequest;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "답변 타겟 수정 요청 DTO")
public record UpdateReplyTargetRequest(
	@Schema(description = "답변 타겟 Id")
	@NotNull(message = "답변 타겟 Id는 Null값 일 수 없습니다.")
	Long id,

	@Schema(description = "수신자 Id")
	@NotNull(message = "수신자 Id는 Null값 일 수 없습니다.")
	Long receiverId,

	@Schema(description = "응답자 Id")
	@NotNull(message = "응답자 Id는 Null값 일 수 없습니다.")
	Long responserId,

	@Schema(description = "답변 수정 요청 DTO")
	@JsonProperty("replies")
	List<@Valid UpdateReplyRequest> updateReplyRequests
) {
}
