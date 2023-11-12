package com.devcourse.ReviewRanger.participation.dto.response;

import java.time.LocalDateTime;

import com.devcourse.ReviewRanger.participation.domain.Participation;
import com.devcourse.ReviewRanger.participation.domain.ReviewStatus;
import com.devcourse.ReviewRanger.review.dto.response.ReviewResponse;
import com.devcourse.ReviewRanger.user.dto.UserResponse;

public record ParticipationResponse(
	Long id,

	ReviewResponse review,

	UserResponse user,//responser

	Boolean isAnswered,

	ReviewStatus status,

	LocalDateTime submitAt,

	LocalDateTime createdAt,

	LocalDateTime updatedAt
) {
	public static ParticipationResponse toResponse(Participation participation, UserResponse user,
		ReviewResponse review) {
		return new ParticipationResponse(
			participation.getId(),
			review,
			user,
			participation.getIsAnswered(),
			participation.getReviewStatus(),
			participation.getSubmitAt(),
			participation.getCreateAt(),
			participation.getUpdatedAt()
		);
	}
}
