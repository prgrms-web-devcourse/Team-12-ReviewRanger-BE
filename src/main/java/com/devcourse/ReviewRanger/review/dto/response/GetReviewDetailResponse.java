package com.devcourse.ReviewRanger.review.dto.response;

import java.util.List;

import com.devcourse.ReviewRanger.participation.domain.DeadlineStatus;

import com.devcourse.ReviewRanger.question.dto.response.GetQuestionResponse;
import com.devcourse.ReviewRanger.review.domain.Review;

public record GetReviewDetailResponse(
	Long id,
	String title,
	DeadlineStatus status,
	List<GetQuestionResponse> questions
) {
	public GetReviewDetailResponse(Review review, List<GetQuestionResponse> questions) {
		this(
			review.getId(),
			review.getTitle(),
			review.getStatus(),
			questions
		);
	}
}
