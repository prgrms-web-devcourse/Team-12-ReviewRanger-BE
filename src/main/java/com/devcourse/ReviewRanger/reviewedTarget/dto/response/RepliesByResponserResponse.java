package com.devcourse.ReviewRanger.reviewedTarget.dto.response;

import java.util.List;

import com.devcourse.ReviewRanger.reply.dto.response.ReplyResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "응답자의 모든 답변 내용 조회 기능 응답 DTO")
public record RepliesByResponserResponse(
	@Schema(description = "수신자 Id")
	Long receiverId,
	@Schema(description = "수신자 이름")
	String receiverName,

	@Schema(description = "응답자의 모든 답변 내용 조회 기능 응답 DTO")
	@JsonProperty("replies")
	List<ReplyResponse> replyResponses
) {

}
