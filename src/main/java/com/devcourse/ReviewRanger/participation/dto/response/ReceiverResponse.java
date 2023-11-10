package com.devcourse.ReviewRanger.participation.dto.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "리뷰를 받은 모든 수신자 조회 응답 DTO")
public record ReceiverResponse(
	@Schema(description = "수신자 Id")
	Long receiverId,

	@Schema(description = "수신자 이름")
	String receiverName,

	@Schema(description = "응답자 수")
	Integer responserCount,

	@Schema(description = "응답자 Id List")
	List<Long> responserIds
) {
}
