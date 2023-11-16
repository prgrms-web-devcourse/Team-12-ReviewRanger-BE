package com.devcourse.ReviewRanger.ReplyTarget.dto.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.devcourse.ReviewRanger.ReplyTarget.domain.ReplyTarget;
import com.devcourse.ReviewRanger.reply.dto.request.CreateReplyRequest;
import com.devcourse.ReviewRanger.user.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "답변 타겟 생성 요청 DTO")
public record CreateReplyTargetRequest(
	@Schema(description = "수신자 Id")
	@NotNull(message = "수신자 Id는 Null값 일 수 없습니다.")
	Long receiverId,

	@Schema(description = "응답자 Id")
	@NotNull(message = "응답자 Id는 Null값 일 수 없습니다.")
	Long responserId,

	@Schema(description = "답변 생성 요청 DTO")
	@JsonProperty("replies")
	List<CreateReplyRequest> createReplyRequests
) {
	public ReplyTarget toEntity(User receiver, User Responer) {
		return new ReplyTarget(receiver, Responer);
	}
}
