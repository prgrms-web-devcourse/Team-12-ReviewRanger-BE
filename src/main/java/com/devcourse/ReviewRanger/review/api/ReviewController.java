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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "review", description = "리뷰 API")
public class ReviewController {

	private final ReviewService reviewService;

	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}

	@Tag(name = "review")
	@Operation(summary = "리뷰 생성 및 요청", description = "생성자가 리뷰를 생성하고 요청하는 API", responses = {
		@ApiResponse(responseCode = "200", description = "리뷰를 생성 및 요청 성공"),
		@ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우")
	})
	@PostMapping("/reviews")
	public RangerResponse<Void> createReview(
		@RequestBody CreateReviewRequest createReviewRequest,
		@AuthenticationPrincipal UserPrincipal user
	) {
		reviewService.createReview(user.getId(), createReviewRequest);

		return RangerResponse.noData();
	}

	@GetMapping("/reviews")
	public RangerResponse<List<GetReviewResponse>> getAllReviewsByRequester(
		@AuthenticationPrincipal UserPrincipal user
	) {
		List<GetReviewResponse> reviewResponses = reviewService.getAllReviewsByRequester(user.getId());

		return RangerResponse.ok(reviewResponses);
	}

	@Tag(name = "review")
	@Operation(summary = "리뷰 마감 요청", description = "생성자 리뷰 마감 API", responses = {
		@ApiResponse(responseCode = "200", description = "리뷰 마감 요청 성공"),
		@ApiResponse(responseCode = "404", description = "리뷰가 존재하지 않는 경우")
	})
	@PostMapping("/reviews/{id}/close")
	public RangerResponse<Void> closeReview(@PathVariable("id") Long reviewId) {
		reviewService.closeReviewOrThrow(reviewId);

		return RangerResponse.noData();
	}
}
