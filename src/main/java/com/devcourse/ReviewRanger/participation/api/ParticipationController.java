package com.devcourse.ReviewRanger.participation.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.devcourse.ReviewRanger.common.response.RangerResponse;
import com.devcourse.ReviewRanger.participation.application.ParticipationService;
import com.devcourse.ReviewRanger.participation.dto.request.SubmitParticipationRequest;
import com.devcourse.ReviewRanger.participation.dto.request.UpdateParticipationRequest;
import com.devcourse.ReviewRanger.participation.dto.response.GetParticipationResponse;
import com.devcourse.ReviewRanger.reviewedTarget.application.ReviewedTargetService;
import com.devcourse.ReviewRanger.reviewedTarget.dto.response.RepliesByResponserResponse;
import com.devcourse.ReviewRanger.user.domain.UserPrincipal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "participation", description = "참여 API")
public class ParticipationController {

	private final ParticipationService participationService;
	private final ReviewedTargetService reviewedTargetService;

	public ParticipationController(ParticipationService participationService,
		ReviewedTargetService reviewedTargetService) {
		this.participationService = participationService;
		this.reviewedTargetService = reviewedTargetService;
	}

	@Tag(name = "participation")
	@Operation(summary = "[토큰] 응답자의 초대받은 리뷰 전체조회", description = "[토큰] 응답자의 초대받은 리뷰 전체조회 API", responses = {
		@ApiResponse(responseCode = "200", description = "조회 성공")
	})
	@GetMapping("/participations")
	public RangerResponse<List<GetParticipationResponse>> getAllParticipationsByResponser(
		@AuthenticationPrincipal UserPrincipal user
	) {
		Long responserId = user.getId();
		List<GetParticipationResponse> responses = participationService.getAllParticipationsByResponser(responserId);

		return RangerResponse.ok(responses);
	}

	/**
	 * 리뷰 답변 기능
	 */
	@PostMapping(value = "/participations")
	public RangerResponse<Void> submitParticipation(@RequestBody @Valid SubmitParticipationRequest request) {
		participationService.submitResponse(request);

		return RangerResponse.noData();
	}

	/**
	 * 리뷰 답변 수정 기능
	 */
	@PutMapping(value = "/participations")
	public RangerResponse<Void> updateParticipation(@RequestBody @Valid UpdateParticipationRequest request) {
		participationService.updateResponse(request);

		return RangerResponse.noData();
	}

	/**
	 * 응답자의 모든 답변 내용 조회
	 */
	@GetMapping("/participations/{id}/written-replies")
	public RangerResponse<List<RepliesByResponserResponse>> getRepliesByResponser(@PathVariable Long id,
		@AuthenticationPrincipal UserPrincipal user) {
		List<RepliesByResponserResponse> responses = reviewedTargetService.getAllRepliesByResponser(
			id);

		return RangerResponse.ok(responses);
	}
}
