package com.devcourse.ReviewRanger.review.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.devcourse.ReviewRanger.participation.domain.Participation;
import com.devcourse.ReviewRanger.question.domain.Question;
import com.devcourse.ReviewRanger.review.application.ReviewService;
import com.devcourse.ReviewRanger.review.domain.Review;
import com.devcourse.ReviewRanger.review.dto.request.CreateReviewRequest;
import com.devcourse.ReviewRanger.review.dto.response.ReviewResponse;
import com.devcourse.ReviewRanger.user.domain.UserPrincipal;

@RestController
public class ReviewController {

	private final ReviewService reviewService;

	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}

	@PostMapping("/reviews")
	public ResponseEntity<Boolean> createReview(
		@RequestBody CreateReviewRequest createReviewRequest,
		@AuthenticationPrincipal UserPrincipal user
	) {
		Review review = createReviewRequest.toReview();
		review.assignRequesterId(1L);
		List<Question> questions = createReviewRequest.toQuestions();
		List<Participation> participations = createReviewRequest.toParticipation();
		Boolean result = reviewService.createReview(review, questions, participations);

		return new ResponseEntity<Boolean>(result, HttpStatus.CREATED);
	}

	@GetMapping("/created-surveys/{requesterId}")
	public ResponseEntity<List<ReviewResponse>> getRequesterReviews(@PathVariable Long requesterId) {
		List<ReviewResponse> requesterSurveys = reviewService.getRequesterReviews(requesterId);

		return new ResponseEntity<>(requesterSurveys, HttpStatus.OK);
	}

	@PostMapping("/surveys/{reviewId}/closed")
	public ResponseEntity<Boolean> closeReview(@PathVariable Long reviewId) {
		Boolean result = reviewService.closeReviewOrThrow(reviewId);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
