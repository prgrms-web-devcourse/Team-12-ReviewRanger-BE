package com.devcourse.ReviewRanger.participation.dto.response;

import java.time.LocalDateTime;

import com.devcourse.ReviewRanger.participation.domain.Participation;
import com.devcourse.ReviewRanger.participation.domain.ReviewStatus;
import com.devcourse.ReviewRanger.review.dto.response.ReviewResponse;
import com.devcourse.ReviewRanger.user.dto.UserResponse;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "참여 기본 응답 DTO")
public record ParticipationResponse(
	@Schema(description = "참여 고유 id")
	Long id,

	@Schema(description = "리뷰")
	ReviewResponse review,

	@Schema(description = "응답자")
	UserResponse user,//responser

	@Schema(description = "응답 참여 여부")
	Boolean isAnswered,

	@Schema(description = "리뷰 상태")
	ReviewStatus status,

	@Schema(description = "리뷰 제출일")
	LocalDateTime submitAt,

	@Schema(description = "생성일")
	LocalDateTime createdAt,

	@Schema(description = "수정일")
	LocalDateTime updatedAt
) {
	public static ParticipationResponse toResponse(Participation participation,
		ReviewResponse review) {
		return new ParticipationResponse(
			participation.getId(),
			review,
			UserResponse.toResponse(participation.getResponser()),
			participation.getIsAnswered(),
			participation.getReviewStatus(),
			participation.getSubmitAt(),
			participation.getCreateAt(),
			participation.getUpdatedAt()
		);
	}
}
