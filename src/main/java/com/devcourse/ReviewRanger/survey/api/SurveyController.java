package com.devcourse.ReviewRanger.survey.api;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.devcourse.ReviewRanger.question.domain.Question;
import com.devcourse.ReviewRanger.survey.application.SurveyService;
import com.devcourse.ReviewRanger.survey.domain.Survey;
import com.devcourse.ReviewRanger.survey.dto.request.CreateSurveyRequest;

@RestController
public class SurveyController {

	private final SurveyService surveyService;

	public SurveyController(SurveyService surveyService) {
		this.surveyService = surveyService;
	}

	@PostMapping("/surveys")
	public String createSurvey(@RequestBody CreateSurveyRequest createSurveyRequest) {

		Survey survey = createSurveyRequest.toSurvey();
		List<Question> questions = createSurveyRequest.toQuestions();

		surveyService.createSurvey(survey, questions);
		return "";
	}

}
