package com.devcourse.ReviewRanger.review.api;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.devcourse.ReviewRanger.common.response.RangerResponse;
import com.devcourse.ReviewRanger.participation.application.ParticipationService;
import com.devcourse.ReviewRanger.participation.dto.response.AllResponserParticipateInReviewResponse;
import com.devcourse.ReviewRanger.participation.dto.response.ReceiverResponse;
import com.devcourse.ReviewRanger.review.application.ReviewService;
import com.devcourse.ReviewRanger.review.dto.request.CreateReviewRequest;
import com.devcourse.ReviewRanger.review.dto.response.GetReviewDetailFirstResponse;
import com.devcourse.ReviewRanger.review.dto.response.GetReviewDetailResponse;
import com.devcourse.ReviewRanger.review.dto.response.GetReviewResponse;
import com.devcourse.ReviewRanger.user.domain.UserPrincipal;
import com.devcourse.ReviewRanger.user.dto.GetUserResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "review", description = "리뷰 API")
public class ReviewController {

	private final ReviewService reviewService;
	private final ParticipationService participationService;

	public ReviewController(ReviewService reviewService, ParticipationService participationService) {
		this.reviewService = reviewService;
		this.participationService = participationService;
	}

	@Tag(name = "review")
	@Operation(summary = "[토큰] 리뷰 생성 및 요청", description = "[토큰] 생성자가 리뷰를 생성하고 요청하는 API", responses = {
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

	@Tag(name = "review")
	@Operation(summary = "[토큰] 요청자가 만든 리뷰 전체 조회", description = "[토큰] 요청자가 만든 리뷰 전체 조회 API", responses = {
		@ApiResponse(responseCode = "200", description = "요청자가 만든 리뷰 전체 조회 성공"),
	})
	@GetMapping("/reviews")
	public RangerResponse<List<GetReviewResponse>> getAllReviewsByRequester(
		@AuthenticationPrincipal UserPrincipal user
	) {
		List<GetReviewResponse> responses = reviewService.getAllReviewsByRequester(user.getId());

		return RangerResponse.ok(responses);
	}

	@Tag(name = "review")
	@Operation(summary = "리뷰 상세 조회", description = "리뷰 상세 조회 API", responses = {
		@ApiResponse(responseCode = "200", description = "리뷰를 상세 조회 성공"),
		@ApiResponse(responseCode = "404", description = "리뷰가 존재하지 않는 경우")
	})
	@GetMapping("/reviews/{id}")
	public RangerResponse<GetReviewDetailResponse> getReviewDetail(
		@PathVariable("id") Long reviewId
	) {
		GetReviewDetailResponse response = reviewService.getReviewDetailOrThrow(reviewId);

		return RangerResponse.ok(response);
	}

	@Tag(name = "review")
	@Operation(summary = "[토큰] 리뷰 첫 상세 조회", description = "[토큰] 리뷰 첫 상세 조회 API", responses = {
		@ApiResponse(responseCode = "200", description = "리뷰를 첫 상세 조회 성공"),
		@ApiResponse(responseCode = "404", description = "리뷰가 존재하지 않는 경우")
	})
	@GetMapping("/reviews/{id}/first")
	public RangerResponse<GetReviewDetailFirstResponse> getReviewDetailFirst(
		@PathVariable("id") Long reviewId,
		@AuthenticationPrincipal UserPrincipal user
	) {
		GetReviewDetailFirstResponse response = reviewService.getReviewDetailFirstOrThrow(reviewId, user.getId());

		return RangerResponse.ok(response);
	}

	@Tag(name = "review")
	@Operation(summary = "응답자를 제외한 리뷰의 수신자 전체 조회", description = "응답자를 제외한 리뷰의 수신자 전체 조회 API", responses = {
		@ApiResponse(responseCode = "200", description = "응답자를 제외한 리뷰의 수신자 전체 조회")
	})
	@GetMapping("/reviews/{reviewId}/responser/{responserId}/receiver")
	public RangerResponse<List<GetUserResponse>> getAllReceivers(
		@PathVariable Long reviewId,
		@PathVariable Long responserId
	) {
		List<GetUserResponse> responses = reviewService.getAllReceivers(reviewId, responserId);

		return RangerResponse.ok(responses);
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

	/**
	 * 설문에 참여한 모든 응답자 조회
	 */
	@GetMapping("/reviews/{id}/responser")
	public RangerResponse<AllResponserParticipateInReviewResponse> getAllReponserParticipateInReview(
		@PathVariable Long id, @AuthenticationPrincipal UserPrincipal user) {
		AllResponserParticipateInReviewResponse response = participationService.getAllResponserParticipateInReviewOrThrow(
			id);

		return RangerResponse.ok(response);
	}

	/**
	 * 리뷰를 받은 모든 수신자 조회
	 */
	@GetMapping("/reviews/{id}/receiver")
	public RangerResponse<List<ReceiverResponse>> getAllReceiverParticipateInReview(
		@PathVariable Long id, @AuthenticationPrincipal UserPrincipal user
	) {
		List<ReceiverResponse> response = participationService.getAllReceiverParticipateInReviewOrThrow(
			id);

		return RangerResponse.ok(response);
	}
}
