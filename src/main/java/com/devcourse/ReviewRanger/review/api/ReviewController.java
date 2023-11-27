package com.devcourse.ReviewRanger.review.api;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devcourse.ReviewRanger.ReplyTarget.application.ReplyTargetService;
import com.devcourse.ReviewRanger.ReplyTarget.dto.response.ReplyTargetResponse;
import com.devcourse.ReviewRanger.auth.domain.UserPrincipal;
import com.devcourse.ReviewRanger.common.response.RangerResponse;
import com.devcourse.ReviewRanger.participation.application.ParticipationService;
import com.devcourse.ReviewRanger.participation.dto.response.ParticipationResponse;
import com.devcourse.ReviewRanger.participation.dto.response.ReceiverResponse;
import com.devcourse.ReviewRanger.review.application.ReviewService;
import com.devcourse.ReviewRanger.review.dto.request.CreateReviewRequest;
import com.devcourse.ReviewRanger.review.dto.response.GetReviewDetailResponse;
import com.devcourse.ReviewRanger.review.dto.response.GetReviewResponse;
import com.devcourse.ReviewRanger.user.dto.GetUserResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "review", description = "리뷰 API")
@RequestMapping("/reviews")
public class ReviewController {

	private final ReviewService reviewService;
	private final ParticipationService participationService;
	private final ReplyTargetService replyTargetService;

	public ReviewController(ReviewService reviewService, ParticipationService participationService,
		ReplyTargetService replyTargetService) {
		this.reviewService = reviewService;
		this.participationService = participationService;
		this.replyTargetService = replyTargetService;
	}

	@Tag(name = "review")
	@Operation(summary = "[토큰] 리뷰 생성 및 요청", description = "[토큰] 생성자가 리뷰를 생성하고 요청하는 API", responses = {
		@ApiResponse(responseCode = "200", description = "리뷰를 생성 및 요청 성공"),
		@ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우")
	})
	@PostMapping
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
	@GetMapping
	public RangerResponse<Slice<GetReviewResponse>> getAllReviewsByRequesterOfCursorPaging(
		@AuthenticationPrincipal UserPrincipal user,
		@RequestParam(required = false) Long cursorId,
		@RequestParam(defaultValue = "12") Integer size
	) {
		Pageable pageable = PageRequest.of(0, size);
		Slice<GetReviewResponse> responses = reviewService.getAllReviewsByRequesterOfCursorPaging(cursorId,
			user.getId(), pageable);

		return RangerResponse.ok(responses);
	}

	@Tag(name = "review")
	@Operation(summary = "[토큰] 생성자 리뷰 상세 조회", description = "[토큰] 생성자 리뷰 상세 조회 API", responses = {
		@ApiResponse(responseCode = "200", description = "리뷰를 상세 조회 성공"),
		@ApiResponse(responseCode = "404", description = "리뷰가 존재하지 않는 경우"),
		@ApiResponse(responseCode = "409", description = "리뷰의 주인과 접근하는 사용자(토큰)이 다른 경우"),
	})
	@GetMapping("/{id}/creator")
	public RangerResponse<GetReviewDetailResponse> getReviewDetailApproachingCreator(
		@PathVariable("id") Long reviewId,
		@AuthenticationPrincipal UserPrincipal user
	) {
		reviewService.checkReviewOwnerEqualityOrThrow(reviewId, user.getId());
		GetReviewDetailResponse response = reviewService.getReviewDetailOrThrow(reviewId, user.getId());

		return RangerResponse.ok(response);
	}

	@Tag(name = "review")
	@Operation(summary = "[토큰] 참여자 리뷰 상세 조회", description = "[토큰] 참여자 리뷰 상세 조회 API", responses = {
		@ApiResponse(responseCode = "200", description = "리뷰를 상세 조회 성공"),
		@ApiResponse(responseCode = "404", description = "리뷰가 존재하지 않는 경우"),
		@ApiResponse(responseCode = "409", description = "리뷰의 주인과 접근하는 사용자(토큰)이 다른 경우"),
	})
	@GetMapping("/{id}/participation")
	public RangerResponse<GetReviewDetailResponse> getReviewDetailApproachingParticipation(
		@PathVariable("id") Long reviewId,
		@AuthenticationPrincipal UserPrincipal user
	) {
		participationService.checkReviewParticipationEqualityOrThrow(reviewId, user.getId());
		GetReviewDetailResponse response = reviewService.getReviewDetailOrThrow(reviewId, user.getId());

		return RangerResponse.ok(response);
	}


	@Tag(name = "review")
	@Operation(summary = "[토큰] 응답자를 제외한 리뷰의 수신자 전체 조회", description = "[토큰] 응답자를 제외한 리뷰의 수신자 전체 조회 API", responses = {
		@ApiResponse(responseCode = "200", description = "응답자를 제외한 리뷰의 수신자 전체 조회")
	})
	@GetMapping("/{reviewId}/responser/receiver")
	public RangerResponse<List<GetUserResponse>> getAllReceivers(
		@PathVariable Long reviewId,
		@AuthenticationPrincipal UserPrincipal user
	) {
		List<GetUserResponse> responses = reviewService.getAllReceivers(reviewId, user.getId());

		return RangerResponse.ok(responses);
	}

	@Tag(name = "review")
	@Operation(summary = "리뷰 마감 요청", description = "생성자 리뷰 마감 API", responses = {
		@ApiResponse(responseCode = "200", description = "리뷰 마감 요청 성공"),
		@ApiResponse(responseCode = "404", description = "리뷰가 존재하지 않는 경우"),
		@ApiResponse(responseCode = "409", description = "모든 응답자가 리뷰를 제출하지 않음에 따른 마감 요청 실패")
	})
	@PostMapping("/{id}/close")
	public RangerResponse<Void> closeReview(@PathVariable("id") Long reviewId) {
		reviewService.closeReviewOrThrow(reviewId);

		return RangerResponse.noData();
	}

	@Tag(name = "review")
	@Operation(summary = "리뷰 삭제", description = "마감 이전 진행중인 리뷰 삭제 API", responses = {
		@ApiResponse(responseCode = "200", description = "리뷰 삭제 성공"),
		@ApiResponse(responseCode = "409", description = "마감 이후 상태에 따른 리뷰 삭제 실패")
	})
	@DeleteMapping("/{id}")
	public RangerResponse<Void> deleteReview(@PathVariable Long id) {
		reviewService.deleteReviewOrThrow(id);

		return RangerResponse.noData();
	}

	@Tag(name = "review")
	@Operation(summary = "리뷰에 참여하는 모든 응답자 조회", description = "리뷰에 참여하는 모든 응답자 조회 API", responses = {
		@ApiResponse(responseCode = "200", description = "리뷰에 참여하는 모든 응답자 조회 성공"),
		@ApiResponse(responseCode = "404", description = "리뷰 존재하지 않는 경우"),
		@ApiResponse(responseCode = "409", description = "리뷰의 주인과 접근하는 사용자(토큰)이 다른 경우"),
	})
	@GetMapping("/{id}/responser")
	public RangerResponse<List<ParticipationResponse>> getAllParticipation(
		@AuthenticationPrincipal UserPrincipal user,
		@PathVariable Long id,
		@RequestParam(value = "searchName", required = false) String searchName,
		@RequestParam(value = "sort", required = false) String sort
	) {
		reviewService.checkReviewOwnerEqualityOrThrow(id, user.getId());
		List<ParticipationResponse> response = participationService.getAllParticipationOrThrow(id, searchName, sort);

		return RangerResponse.ok(response);
	}

	@Tag(name = "review")
	@Operation(summary = "리뷰를 받은 모든 수신자 조회", description = "리뷰를 받은 모든 수신자 조회 API", responses = {
		@ApiResponse(responseCode = "200", description = "리뷰를 받은 모든 수신자 조회 성공"),
		@ApiResponse(responseCode = "409", description = "리뷰의 주인과 접근하는 사용자(토큰)이 다른 경우"),
	})
	@GetMapping("/{id}/receiver")
	public RangerResponse<List<ReceiverResponse>> getAllReceiverParticipateInReview(
		@AuthenticationPrincipal UserPrincipal user,
		@PathVariable Long id,
		@RequestParam(value = "searchName", required = false) String searchName) {
		reviewService.checkReviewOwnerEqualityOrThrow(id, user.getId());
		List<ReceiverResponse> response = participationService.getAllReceiver(id, searchName);

		return RangerResponse.ok(response);
	}

	@Tag(name = "review")
	@Operation(summary = "응답자가 접근한 응답자 답변 조회 기능", description = "응답자별 답변 조회 API", responses = {
		@ApiResponse(responseCode = "200", description = "응답자별 답변 조회 성공"),
		@ApiResponse(responseCode = "404", description = "수신자가 존재하지 않는 경우"),
		@ApiResponse(responseCode = "409", description = "응답자와 접근하는 사용자(토큰)이 다른 경우"),
	})
	@GetMapping("/{reviewId}/responser/{responserId}/participation")
	public RangerResponse<List<ReplyTargetResponse>> getRepliesByResponserApproachingParticipation(
		@AuthenticationPrincipal UserPrincipal user,
		@PathVariable Long reviewId,
		@PathVariable Long responserId) {
		participationService.checkReviewParticipationEqualityOrThrow(reviewId, user.getId());
		List<ReplyTargetResponse> responses = replyTargetService.getAllRepliesByResponser(
			reviewId, responserId);

		return RangerResponse.ok(responses);
	}

	@Tag(name = "review")
	@Operation(summary = "리뷰 생성자가 접근한 응답자별 답변 조회 기능", description = "응답자별 답변 조회 API", responses = {
		@ApiResponse(responseCode = "200", description = "응답자별 답변 조회 성공"),
		@ApiResponse(responseCode = "404", description = "수신자가 존재하지 않는 경우"),
		@ApiResponse(responseCode = "409", description = "리뷰의 주인과 접근하는 사용자(토큰)이 다른 경우"),
	})
	@GetMapping("/{reviewId}/responser/{responserId}/creator")
	public RangerResponse<List<ReplyTargetResponse>> getRepliesByResponserApproachingCreator(
		@AuthenticationPrincipal UserPrincipal user,
		@PathVariable Long reviewId,
		@PathVariable Long responserId) {
		reviewService.checkReviewOwnerEqualityOrThrow(reviewId, user.getId());
		List<ReplyTargetResponse> responses = replyTargetService.getAllRepliesByResponser(
			reviewId, responserId);

		return RangerResponse.ok(responses);
	}

	@Tag(name = "review")
	@Operation(summary = "수신자별 답변 조회 기능", description = "수신자별 답변 조회 API", responses = {
		@ApiResponse(responseCode = "200", description = "수신자별 답변 조회 성공"),
		@ApiResponse(responseCode = "404", description = "수신자가 존재하지 않는 경우"),
		@ApiResponse(responseCode = "409", description = "리뷰의 주인과 접근하는 사용자(토큰)이 다른 경우"),
	})
	@GetMapping("/{reviewId}/receiver/{receiverId}")
	public RangerResponse<List<ReplyTargetResponse>> getRepliesByReceiver(
		@AuthenticationPrincipal UserPrincipal user,
		@PathVariable Long reviewId,
		@PathVariable Long receiverId) {
		reviewService.checkReviewOwnerEqualityOrThrow(reviewId, user.getId());
		List<ReplyTargetResponse> responses = replyTargetService.getAllRepliesByReceiver(reviewId, receiverId);

		return RangerResponse.ok(responses);
	}
}
