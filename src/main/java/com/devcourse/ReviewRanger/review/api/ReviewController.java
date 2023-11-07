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

import com.devcourse.ReviewRanger.common.response.RangerResponse;

import com.devcourse.ReviewRanger.review.application.ReviewService;
import com.devcourse.ReviewRanger.review.dto.request.CreateReviewRequest;
import com.devcourse.ReviewRanger.review.dto.response.GetReviewResponse;
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
		boolean result = reviewService.createReview(user.getId(), createReviewRequest);

		return new ResponseEntity<Boolean>(result, HttpStatus.CREATED);
	}

	@GetMapping("/reviews")
	public RangerResponse<List<GetReviewResponse>> getAllReviewsByRequester(@AuthenticationPrincipal UserPrincipal user) {
		Long requesterId = user.getId();
		List<GetReviewResponse> reviewResponses = reviewService.getAllReviewsByRequester(requesterId);

		return RangerResponse.ok(reviewResponses);
	}

	@PostMapping("/surveys/{reviewId}/closed")
	public ResponseEntity<Boolean> closeReview(@PathVariable Long reviewId) {
		Boolean result = reviewService.closeReviewOrThrow(reviewId);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
