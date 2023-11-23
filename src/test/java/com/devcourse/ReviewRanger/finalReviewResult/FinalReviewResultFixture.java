package com.devcourse.ReviewRanger.finalReviewResult;

import static com.devcourse.ReviewRanger.FinalQuestionFixture.*;
import static com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResult.*;
import static com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResult.Status.*;

import java.util.List;

import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalQuestion;
import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResult;

public enum FinalReviewResultFixture {

	BASIC_FIXTURE(1L,
		"장수연",
		1L,
		"1차 피어리뷰 입니다.",
		"마감일은 2025-25-25입니다.",
		NOT_SENT,
		List.of(
			ONCE_QUESTION.toEntity(),
			TWICE_QUESTION.toEntity(),
			TWICE_QUESTION.toEntity(),
			FOUR_QUESTION.toEntity(),
			FIVE_QUESTION.toEntity(),
			SIX_QUESTION.toEntity()
		)
	);

	private final Long userId;

	private final String userName;

	private final Long reviewId;

	private final String title;

	private final String description;

	private final Status status;

	private final List<FinalQuestion> questions;

	FinalReviewResultFixture(Long userId, String userName, Long reviewId, String title, String description,
		Status status, List<FinalQuestion> questions) {
		this.userId = userId;
		this.userName = userName;
		this.reviewId = reviewId;
		this.title = title;
		this.description = description;
		this.status = status;
		this.questions = questions;
	}

	public FinalReviewResult toEntity() {
		return new FinalReviewResult(userId, userName, reviewId, title, description, status, questions);
	}

	public FinalReviewResult toEntity(List<FinalQuestion> questions) {
		return new FinalReviewResult(userId, userName, reviewId, title, description, status, questions);
	}
}
