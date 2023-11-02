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

	@GetMapping("/created-surveys/{requesterId}")
	public ResponseEntity<List<SurveyResponse>> getRequesterSurveys(@PathVariable Long requesterId) {
		List<SurveyResponse> requesterSurveys = surveyService.getRequesterSurveys(requesterId);

		return new ResponseEntity<>(requesterSurveys, HttpStatus.OK);
	}

	@PostMapping("/surveys/{surveyId}/closed")
	public ResponseEntity<Boolean> createSurvey(@PathVariable Long surveyId) {
		Boolean result = surveyService.closeSurveyOrThrow(surveyId);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
