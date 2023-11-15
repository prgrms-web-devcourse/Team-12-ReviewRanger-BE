package com.devcourse.ReviewRanger.finalReviewResult.api;

import static org.springframework.http.HttpStatus.*;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.devcourse.ReviewRanger.common.response.RangerResponse;
import com.devcourse.ReviewRanger.finalReviewResult.application.FinalReviewResultService;
import com.devcourse.ReviewRanger.finalReviewResult.dto.CreateFinalReviewRequest;
import com.devcourse.ReviewRanger.finalReviewResult.dto.CreateFinalReviewResponse;
import com.devcourse.ReviewRanger.finalReviewResult.dto.FinalReviewResultListResponse;
import com.devcourse.ReviewRanger.finalReviewResult.dto.GetFinalReviewAnswerResponse;
import com.devcourse.ReviewRanger.finalReviewResult.dto.GetFinalReviewResultResponse;
import com.devcourse.ReviewRanger.user.domain.UserPrincipal;

@RestController
@RequestMapping("/final-results")
public class FinalReviewResultController {

	private final FinalReviewResultService finalReviewResultService;

	public FinalReviewResultController(FinalReviewResultService finalReviewResultService) {
		this.finalReviewResultService = finalReviewResultService;
	}

	@GetMapping
	@ResponseStatus(OK)
	public RangerResponse<List<FinalReviewResultListResponse>> getAllFinalReviewResults(
		@AuthenticationPrincipal UserPrincipal user
	) {
		List<FinalReviewResultListResponse> allFinalReviewResults
			= finalReviewResultService.getAllFinalReviewResults(user.getId());

		return RangerResponse.ok(allFinalReviewResults);
	}

	@PostMapping
	@ResponseStatus(CREATED)
	public RangerResponse<CreateFinalReviewResponse> createFinalReviewResults(
		@RequestBody @Valid CreateFinalReviewRequest finalReviewRequest
	) {
		CreateFinalReviewResponse finalReviewResponse
			= finalReviewResultService.createFinalReviewResult(finalReviewRequest);

		return RangerResponse.ok(finalReviewResponse);
	}

	@GetMapping("/{reviewId}/status")
	@ResponseStatus(OK)
	public RangerResponse<Void> checkFinalResultStatus(
		@PathVariable Long reviewId
	) {
		Boolean checkStatus = finalReviewResultService.checkFinalResultStatus(reviewId);

		return RangerResponse.ok(checkStatus);
	}

	@PostMapping("/{reviewId}")
	@ResponseStatus(OK)
	public RangerResponse<Void> sendFinalResultToUsers(
		@PathVariable Long reviewId
	) {
		finalReviewResultService.sendFinalResultToUsers(reviewId);

		return RangerResponse.noData();
	}

	@GetMapping("/{finalReviewId}")
	@ResponseStatus(OK)
	public RangerResponse<GetFinalReviewResultResponse> getFinalReviewResultInfo(
		@PathVariable Long finalReviewId
	) {
		GetFinalReviewResultResponse finalReviewResultInfo
			= finalReviewResultService.getFinalReviewResultInfo(finalReviewId);

		return RangerResponse.ok(finalReviewResultInfo);
	}

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
