package com.devcourse.ReviewRanger.participation;

import static com.devcourse.ReviewRanger.participation.domain.ReviewStatus.*;
import static com.devcourse.ReviewRanger.user.UserFixture.*;

import java.time.LocalDateTime;

import com.devcourse.ReviewRanger.participation.domain.Participation;
import com.devcourse.ReviewRanger.participation.domain.ReviewStatus;
import com.devcourse.ReviewRanger.user.domain.User;

public enum ParticipationFixture {
	PARTICIPATION_FIXTURE(1L, SUYEON_FIXTURE.toEntity(), true, PROCEEDING, null);

	private final Long reviewId;
	private final User responser;
	private final Boolean isAnswered;
	private final ReviewStatus reviewStatus;
	private final LocalDateTime submitAt;

	ParticipationFixture(Long reviewId, User responser, Boolean isAnswered, ReviewStatus reviewStatus,
		LocalDateTime submitAt) {
		this.reviewId = reviewId;
		this.responser = responser;
		this.isAnswered = isAnswered;
		this.reviewStatus = reviewStatus;
		this.submitAt = submitAt;
	}

	public Participation toEntity() {
		return new Participation(reviewId, responser, isAnswered, reviewStatus, submitAt);
	}
}
