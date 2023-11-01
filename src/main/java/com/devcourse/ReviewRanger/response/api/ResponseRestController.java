package com.devcourse.ReviewRanger.response.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.devcourse.ReviewRanger.response.application.ResponseService;
import com.devcourse.ReviewRanger.response.dto.request.CreateResponse;
import com.devcourse.ReviewRanger.surveyresult.application.SurveyResultService;
import com.devcourse.ReviewRanger.surveyresult.domain.SurveyResult;

import jakarta.validation.Valid;

@RestController
public class ResponseRestController {

	private final ResponseService responseService;
	private final SurveyResultService surveyResultService;

	public ResponseRestController(ResponseService responseService, SurveyResultService surveyResultService) {
		this.responseService = responseService;
		this.surveyResultService = surveyResultService;
	}

	@PostMapping(value = "/invited-surveys")
	public ResponseEntity<Boolean> create(@RequestBody @Valid CreateResponse request) {
		SurveyResult surveyResult = surveyResultService.findSurveyResult(request.surveyId(), request.responserId());
		Boolean response = responseService.createResponse(request.responserId(), surveyResult.getId(), request);

		return ResponseEntity.status(HttpStatus.CREATED)
			.body(response);
	}
}
