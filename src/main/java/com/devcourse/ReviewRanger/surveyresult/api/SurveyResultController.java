package com.devcourse.ReviewRanger.surveyresult.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.devcourse.ReviewRanger.surveyresult.application.SurveyResultService;
import com.devcourse.ReviewRanger.surveyresult.domain.SurveyResult;
import com.devcourse.ReviewRanger.surveyresult.dto.response.AllResponserResultResponseDto;

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
	public ResponseEntity<AllResponserResultResponseDto> getAllReponserParticipateInSurvey(
		@PathVariable Long surveyId) {
		AllResponserResultResponseDto allReponserSurveyResult = surveyResultService.getAllReponserParticipateInSurveyOrThrow(
			surveyId);

		return ResponseEntity.status(HttpStatus.OK).body(allReponserSurveyResult);
	}
}
