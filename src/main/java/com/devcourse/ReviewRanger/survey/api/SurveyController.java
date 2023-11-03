package com.devcourse.ReviewRanger.survey.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.devcourse.ReviewRanger.question.domain.Question;
import com.devcourse.ReviewRanger.survey.application.SurveyService;
import com.devcourse.ReviewRanger.survey.domain.Survey;
import com.devcourse.ReviewRanger.survey.dto.request.CreateSurveyRequest;
import com.devcourse.ReviewRanger.survey.dto.response.SurveyResponseWithResponserCount;
import com.devcourse.ReviewRanger.survey.dto.response.SurveyResponse;
import com.devcourse.ReviewRanger.surveyresult.domain.SurveyResult;

@RestController
public class SurveyController {

	private final SurveyService surveyService;

	public SurveyController(SurveyService surveyService) {
		this.surveyService = surveyService;
	}

	@PostMapping("/surveys")
	public ResponseEntity<Boolean> createSurvey(@RequestBody CreateSurveyRequest createSurveyRequest) {
		Survey survey = createSurveyRequest.toSurvey();
		survey.assignRequesterId(1L);
		List<Question> questions = createSurveyRequest.toQuestions();

		List<SurveyResult> surveyResults = createSurveyRequest.toSurveyResult();
		Boolean result = surveyService.createSurvey(survey, questions, surveyResults);

		return new ResponseEntity<Boolean>(result, HttpStatus.CREATED);
	}

	@GetMapping("/surveys/{id}")
	public ResponseEntity<SurveyResponse> getSurvey(@PathVariable Long id) {
		SurveyResponse tempSurveyResponse = surveyService.getSurvey(id);
		return new ResponseEntity<>(tempSurveyResponse, HttpStatus.OK);
	}

	@GetMapping("/created-surveys/{requesterId}")
	public ResponseEntity<List<SurveyResponseWithResponserCount>> getAllCreatedSurveysByRequester(@PathVariable Long requesterId) {
		List<SurveyResponseWithResponserCount> requesterSurveys = surveyService.getAllCreatedSurveysByRequester(requesterId);

		return new ResponseEntity<>(requesterSurveys, HttpStatus.OK);
	}

	@PostMapping("/surveys/{surveyId}/closed")
	public ResponseEntity<Boolean> closeSurvey(@PathVariable Long surveyId) {
		Boolean result = surveyService.closeSurveyOrThrow(surveyId);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
