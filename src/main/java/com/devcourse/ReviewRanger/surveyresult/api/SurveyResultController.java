package com.devcourse.ReviewRanger.surveyresult.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.devcourse.ReviewRanger.common.response.RangerResponse;
import com.devcourse.ReviewRanger.surveyresult.application.SurveyResultService;
import com.devcourse.ReviewRanger.surveyresult.domain.SurveyResult;
import com.devcourse.ReviewRanger.surveyresult.dto.request.SubmitSurveyResultRequest;
import com.devcourse.ReviewRanger.surveyresult.dto.response.AllResponserParticipateInSurveyDto;
import com.devcourse.ReviewRanger.surveyresult.dto.response.Recipient;

@RestController
public class SurveyResultController {

	private final SurveyResultService surveyResultService;

	public SurveyResultController(SurveyResultService surveyResultService) {
		this.surveyResultService = surveyResultService;
	}

	@GetMapping("/invited-surveys/{responserId}")
	public ResponseEntity<List<SurveyResult>> getResponserSurveyResult(@PathVariable Long responserId) {
		List<SurveyResult> sersurveyResults = surveyResultService.getResponserSurveyResult(responserId);

		return new ResponseEntity<List<SurveyResult>>(sersurveyResults, HttpStatus.OK);
	}

	/**
	 * 설문에 참여한 모든 응답자 조회
	 */
	@GetMapping("/created-surveys/{surveyId}/responser")
	public RangerResponse<AllResponserParticipateInSurveyDto> getAllReponserParticipateInSurvey(
		@PathVariable Long surveyId) {
		AllResponserParticipateInSurveyDto response = surveyResultService.getAllReponserParticipateInSurveyOrThrow(
			surveyId);

		return RangerResponse.ok(response);
	}

	/**
	 * 리뷰를 받은 사용자 전체 조회 기능
	 */
	@GetMapping("/created-surveys/{surveyId}/recipient")
	public RangerResponse<List<Recipient>> getAllRecipientParticipateInSurvey(
		@PathVariable Long surveyId
	) {
		List<Recipient> response = surveyResultService.getAllRecipientParticipateInSurveyOrThrow(
			surveyId);

		return RangerResponse.ok(response);
	}

	/**
	 * 리뷰 답변 기능
	 */
	@PostMapping(value = "/invited-surveys")
	public RangerResponse<Void> submitSurveyResult(@RequestBody SubmitSurveyResultRequest request) {
		Boolean response = surveyResultService.submitResponse(request);

		return RangerResponse.ok(response);
	}
}
