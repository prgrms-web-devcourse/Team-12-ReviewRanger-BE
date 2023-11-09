package com.devcourse.ReviewRanger.participation.dto.response;

import java.time.LocalDateTime;

import com.devcourse.ReviewRanger.participation.domain.DeadlineStatus;
import com.devcourse.ReviewRanger.participation.domain.Participation;

public record GetParticipationResponse(
	Long participationId,
	Long reviewId,
	String title,
	LocalDateTime submitAt,
	DeadlineStatus status
) {
	public GetParticipationResponse(Participation participation, String title) {
		this(
			participation.getId(),
			participation.getReviewId(),
			title,
			participation.getSubmitAt(),
			participation.getDeadlineStatus()
		);
	}
}
