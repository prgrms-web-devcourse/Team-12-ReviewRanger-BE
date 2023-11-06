package com.devcourse.ReviewRanger.review.dto.request;

import java.util.List;

import com.devcourse.ReviewRanger.question.dto.request.CreateQuestionRequest;
import com.devcourse.ReviewRanger.review.domain.Review;
import com.devcourse.ReviewRanger.review.domain.ReviewType;
import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateReviewRequest(
	String title,
	String description,
	ReviewType type,
	@JsonProperty("questions") List<CreateQuestionRequest> creatQuestionRequests,
	@JsonProperty("responserIdList") List<Long> responserIds
) {

	public Review toEntity() {
		return new Review(title, description, type);
	}

}
