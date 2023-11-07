package com.devcourse.ReviewRanger.finalReviewResult.api;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devcourse.ReviewRanger.common.response.RangerResponse;
import com.devcourse.ReviewRanger.finalReviewResult.application.FinalReviewResultService;
import com.devcourse.ReviewRanger.finalReviewResult.dto.FinalReviewResultListResponse;
import com.devcourse.ReviewRanger.user.domain.UserPrincipal;

@RestController
@RequestMapping("/final-results")
public class FinalReviewResultController {

	private final FinalReviewResultService finalReviewResultService;

	public FinalReviewResultController(FinalReviewResultService finalReviewResultService) {
		this.finalReviewResultService = finalReviewResultService;
	}

	@GetMapping
	public RangerResponse<List<FinalReviewResultListResponse>> getAllFinalReviewResults(
		@AuthenticationPrincipal UserPrincipal user
	) {
		List<FinalReviewResultListResponse> allFinalReviewResults
			= finalReviewResultService.getAllFinalReviewResults(user.getId());

		return RangerResponse.ok(allFinalReviewResults);
	}

}
