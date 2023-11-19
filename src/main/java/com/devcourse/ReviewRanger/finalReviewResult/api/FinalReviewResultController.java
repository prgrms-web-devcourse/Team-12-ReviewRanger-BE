package com.devcourse.ReviewRanger.finalReviewResult.api;

import static org.springframework.http.HttpStatus.*;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.devcourse.ReviewRanger.common.response.RangerResponse;
import com.devcourse.ReviewRanger.finalReviewResult.application.FinalReviewResultService;
import com.devcourse.ReviewRanger.finalReviewResult.dto.CreateFinalReviewRequest;
import com.devcourse.ReviewRanger.finalReviewResult.dto.CreateFinalReviewResponse;
import com.devcourse.ReviewRanger.finalReviewResult.dto.FinalReviewResultListResponse;
import com.devcourse.ReviewRanger.finalReviewResult.dto.GetFinalReviewAnswerResponse;
import com.devcourse.ReviewRanger.finalReviewResult.dto.GetFinalReviewResultResponse;
import com.devcourse.ReviewRanger.auth.domain.UserPrincipal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "final-result", description = "최종 리뷰 결과 API")
@RestController
@RequestMapping("/final-results")
public class FinalReviewResultController {

	private final FinalReviewResultService finalReviewResultService;

	public FinalReviewResultController(FinalReviewResultService finalReviewResultService) {
		this.finalReviewResultService = finalReviewResultService;
	}

	@Tag(name = "final-result")
	@Operation(summary = "[토큰] 마이페이지 리뷰 결과 목록 조회", description = "[토큰] 사용자에게 전송 된 리뷰 결과 목록 조회 API", responses = {
		@ApiResponse(responseCode = "200", description = "전송 된 리뷰 결과 목록 조회 성공"),
		@ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
	})
	@GetMapping("/list")
	@ResponseStatus(OK)
	public RangerResponse<List<FinalReviewResultListResponse>> getAllFinalReviewResults(
		@AuthenticationPrincipal UserPrincipal user
	) {
		List<FinalReviewResultListResponse> allFinalReviewResults
			= finalReviewResultService.getAllFinalReviewResults(user.getId());

		return RangerResponse.ok(allFinalReviewResults);
	}

	@Tag(name = "final-result")
	@Operation(summary = "[토큰] 마이페이지 리뷰 결과 목록 커서 페이징", description = "[토큰] 리뷰 결과 목록을 커서 기반 페이징 API", responses = {
		@ApiResponse(responseCode = "200", description = "마이페이지 리뷰 결과 목록 페이징 성공"),
		@ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
		@ApiResponse(responseCode = "404", description = "유효하지 않은 사용자 id인 경우"),
	})
	@GetMapping
	@ResponseStatus(OK)
	public RangerResponse<Slice<FinalReviewResultListResponse>> getAllFinalReviewResultsOfCursorPaging(
		@AuthenticationPrincipal UserPrincipal user,
		@RequestParam(required = false) Long cursorId,
		@RequestParam(defaultValue = "12") Integer size
	) {
		Pageable pageable = PageRequest.of(0, size);
		Slice<FinalReviewResultListResponse> finalReviewResultsOfCursorPaging
			= finalReviewResultService.getAllFinalReviewResultsOfCursorPaging(cursorId, user.getId(), pageable);

		return RangerResponse.ok(finalReviewResultsOfCursorPaging);
	}

	@Tag(name = "final-result")
	@Operation(summary = "전송 전 최종 리뷰 결과 저장", description = "전송 전 최종 리뷰 결과를 저장 API", responses = {
		@ApiResponse(responseCode = "201", description = "전송 전 리뷰 결과 저장 성공"),
		@ApiResponse(responseCode = "404", description = "유효하지 않은 식별 값인 경우"),
	})
	@PostMapping
	@ResponseStatus(CREATED)
	public RangerResponse<CreateFinalReviewResponse> createFinalReviewResults(
		@RequestBody @Valid CreateFinalReviewRequest finalReviewRequest
	) {
		CreateFinalReviewResponse finalReviewResponse
			= finalReviewResultService.createFinalReviewResult(finalReviewRequest);

		return RangerResponse.ok(finalReviewResponse);
	}

	@Tag(name = "final-result")
	@Operation(summary = "전송 버튼 활성화", description = "전송 전에 해당 리뷰 모든 참여자가 리뷰 결과를 가지는지 검증하는 API", responses = {
		@ApiResponse(responseCode = "200", description = "전송 버튼 활성화"),
		@ApiResponse(responseCode = "404", description = "유효하지 않은 식별 값인 경우"),
	})
	@GetMapping("/{reviewId}/status")
	@ResponseStatus(OK)
	public RangerResponse<Void> checkFinalResultStatus(
		@PathVariable Long reviewId
	) {
		Boolean checkStatus = finalReviewResultService.checkFinalResultStatus(reviewId);

		return RangerResponse.ok(checkStatus);
	}

	@Tag(name = "final-result")
	@Operation(summary = "리뷰 결과 전송", description = "리뷰 결과를 리뷰 대상에게 전송하는 API", responses = {
		@ApiResponse(responseCode = "200", description = "해당 리뷰의 리뷰 결과를 참여자에게 전송"),
		@ApiResponse(responseCode = "404", description = "유효하지 않은 식별 값인 경우"),
	})
	@PostMapping("/{reviewId}")
	@ResponseStatus(OK)
	public RangerResponse<Void> sendFinalResultToUsers(
		@PathVariable Long reviewId
	) {
		finalReviewResultService.sendFinalResultToUsers(reviewId);

		return RangerResponse.noData();
	}

	@Tag(name = "final-result")
	@Operation(summary = "[토큰] 리뷰 결과 정보 조회 (Q&A 포함X)", description = "[토큰] 질답 정보 없이 리뷰 결과에 대한 정보를 조회하는 API", responses = {
		@ApiResponse(responseCode = "200", description = "리뷰 결과 정보 조회 성공"),
		@ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
		@ApiResponse(responseCode = "404", description = "유효하지 않은 리뷰 결과 id인 경우"),
		@ApiResponse(responseCode = "409", description = "리뷰 결과 주인이 아닌 경우")
	})
	@GetMapping("/{finalReviewId}")
	@ResponseStatus(OK)
	public RangerResponse<GetFinalReviewResultResponse> getFinalReviewResultInfo(
		@AuthenticationPrincipal UserPrincipal user,
		@PathVariable Long finalReviewId
	) {
		GetFinalReviewResultResponse finalReviewResultInfo
			= finalReviewResultService.getFinalReviewResultInfo(user.getId(), finalReviewId);

		return RangerResponse.ok(finalReviewResultInfo);
	}

	@Tag(name = "final-result")
	@Operation(summary = "리뷰 결과 Q&A 정보 조회", description = "리뷰 결과의 질문과 답변에 대한 목록을 조회하는 API", responses = {
		@ApiResponse(responseCode = "200", description = "리뷰 결과의 질답 목록 정보 조회 성공"),
		@ApiResponse(responseCode = "404", description = "유효하지 않은 식별 값인 경우"),
	})
	@GetMapping("/{finalReviewId}/qna")
	@ResponseStatus(OK)
	public RangerResponse<List<GetFinalReviewAnswerResponse>> getFinalReviewAnswers(
		@PathVariable Long finalReviewId
	) {
		List<GetFinalReviewAnswerResponse> finalReviewAnswerInfo
			= finalReviewResultService.getFinalReviewAnswerList(finalReviewId);

		return RangerResponse.ok(finalReviewAnswerInfo);
	}
}
