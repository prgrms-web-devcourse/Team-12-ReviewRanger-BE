package com.devcourse.ReviewRanger.review.dto.request;

import java.util.List;

import com.devcourse.ReviewRanger.participation.domain.Participation;
import com.devcourse.ReviewRanger.question.domain.Question;
import com.devcourse.ReviewRanger.review.domain.Review;
import com.devcourse.ReviewRanger.review.dto.QuestionRequest;
import com.devcourse.ReviewRanger.review.dto.ReviewRequest;
import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateReviewRequest(
	@JsonProperty("reivew") ReviewRequest reviewRequest,
	@JsonProperty("questions") List<QuestionRequest> questionRequests,
	@JsonProperty("responserIdList") List<Long> responserIds

) {

	public Review toReview() {
		return reviewRequest.toEntity();
	}

	public List<Question> toQuestions() {
		return questionRequests.stream().map(questionRequest -> questionRequest.toEntity()).toList();
	}

	public List<Participation> toParticipation() {
		return responserIds.stream().map(responserId -> new Participation(responserId)).toList();
	}

}
