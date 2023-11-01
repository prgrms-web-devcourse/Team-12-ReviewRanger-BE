package com.devcourse.ReviewRanger.survey.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.devcourse.ReviewRanger.question.domain.Question;
import com.devcourse.ReviewRanger.survey.application.SurveyService;
import com.devcourse.ReviewRanger.survey.domain.Survey;
import com.devcourse.ReviewRanger.survey.dto.request.CreateSurveyRequest;
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
}
