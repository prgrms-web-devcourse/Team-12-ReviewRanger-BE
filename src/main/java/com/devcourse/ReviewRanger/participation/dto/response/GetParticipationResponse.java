package com.devcourse.ReviewRanger.participation.dto.response;

import java.time.LocalDateTime;

import com.devcourse.ReviewRanger.participation.domain.ReviewStatus;
import com.devcourse.ReviewRanger.participation.domain.Participation;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "초대받은 리뷰(참여) 전체조회 응답 DTO")
public record GetParticipationResponse(
	@Schema(description = "참여 고유 id")
	Long participationId,

	@Schema(description = "리뷰 고유 id")
	Long reviewId,

	@Schema(description = "초대받은 리뷰 제목")
	String title,

	@Schema(description = "초대받은 리뷰 생성일자")
	LocalDateTime createAt,

	@Schema(description = "초대받은 리뷰 제출일자")
	LocalDateTime submitAt,

	@Schema(description = "초대받은 리뷰 진행상태")
	ReviewStatus status
) {
	public GetParticipationResponse(Participation participation, String title) {
		this(
			participation.getId(),
			participation.getReviewId(),
			title,
			participation.getCreateAt(),
			participation.getSubmitAt(),
			participation.getReviewStatus()
		);
	}
}
