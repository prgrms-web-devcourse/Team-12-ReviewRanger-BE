package com.devcourse.ReviewRanger.participation.api;

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
import com.devcourse.ReviewRanger.participation.application.ParticipationService;
import com.devcourse.ReviewRanger.participation.dto.request.SubmitParticipationRequest;
import com.devcourse.ReviewRanger.participation.dto.response.AllResponserParticipateInReviewResponse;
import com.devcourse.ReviewRanger.participation.dto.response.GetParticipationResponse;
import com.devcourse.ReviewRanger.participation.dto.response.SubjectResponse;
import com.devcourse.ReviewRanger.user.domain.UserPrincipal;

@RestController
public class ParticipationController {

	private final ParticipationService participationService;

	public ParticipationController(ParticipationService participationService) {
		this.participationService = participationService;
	}

	@GetMapping("/participations")
	public ResponseEntity<List<GetParticipationResponse>> getAllReviewsByResponser(
		@AuthenticationPrincipal UserPrincipal user) {
		Long responserId = user.getId();
		List<GetParticipationResponse> responses = participationService.getAllReviewsByResponser(responserId);

		return new ResponseEntity<>(responses, HttpStatus.OK);
	}

	/**
	 * 설문에 참여한 모든 응답자 조회
	 */
	// @GetMapping("/created-surveys/{surveyId}/responser")
	// public RangerResponse<AllResponserParticipateInReviewResponse> getAllReponserParticipateInSurvey(
	// 	@PathVariable Long surveyId) {
	// 	AllResponserParticipateInReviewResponse response = participationService.getAllReponserParticipateInReviewOrThrow(
	// 		surveyId);
	//
	// 	return RangerResponse.ok(response);
	// }

	/**
	 * 리뷰를 받은 사용자 전체 조회 기능
	 */
	@GetMapping("/created-surveys/{surveyId}/recipient")
	public RangerResponse<List<SubjectResponse>> getAllRecipientParticipateInSurvey(@PathVariable Long surveyId) {
		List<SubjectResponse> response = participationService.getAllRecipientParticipateInReviewOrThrow(surveyId);

		return RangerResponse.ok(response);
	}

	/**
	 * 리뷰 답변 기능
	 */
	@PostMapping(value = "/invited-surveys")
	public RangerResponse<Void> submitSurveyResult(@RequestBody SubmitParticipationRequest request) {
		Boolean response = participationService.submitResponse(request);

		return RangerResponse.ok(response);
	}
}
