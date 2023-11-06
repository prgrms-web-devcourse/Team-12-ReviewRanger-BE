package com.devcourse.ReviewRanger.review.dto.request;

import java.util.List;

import com.devcourse.ReviewRanger.participation.domain.Participation;
import com.devcourse.ReviewRanger.question.domain.Question;
import com.devcourse.ReviewRanger.question.dto.request.CreateQuestionRequest;
import com.devcourse.ReviewRanger.review.domain.Review;
import com.devcourse.ReviewRanger.review.domain.ReviewType;
import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateReviewRequest(
	String title,
	String description,
	ReviewType type,
	@JsonProperty("questions") List<CreateQuestionRequest> creaetQuestionRequests,
	@JsonProperty("responserIdList") List<Long> responserIds
) {

	public Review toReview() {
		return null;
	}

	public List<Question> toQuestions() {
		return creaetQuestionRequests.stream().map(createQuestionRequest -> createQuestionRequest.toEntity()).toList();
	}

	public List<Participation> toParticipation() {
		return responserIds.stream().map(responserId -> new Participation(responserId)).toList();
	}

}
