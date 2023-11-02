package com.devcourse.ReviewRanger.survey.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.question.application.QuestionService;
import com.devcourse.ReviewRanger.question.domain.Question;
import com.devcourse.ReviewRanger.survey.domain.Survey;
import com.devcourse.ReviewRanger.survey.dto.response.SurveyResponse;
import com.devcourse.ReviewRanger.survey.repository.SurveyRepository;
import com.devcourse.ReviewRanger.surveyresult.application.SurveyResultService;
import com.devcourse.ReviewRanger.surveyresult.domain.SurveyResult;

@Service
@Transactional(readOnly = true)
public class SurveyService {

	private final QuestionService questionService;
	private final SurveyResultService surveyResultService;

	private final SurveyRepository surveyRepository;

	public SurveyService(QuestionService questionService, SurveyResultService surveyResultService,
		SurveyRepository surveyRepository) {
		this.questionService = questionService;
		this.surveyResultService = surveyResultService;
		this.surveyRepository = surveyRepository;
	}

	@Transactional
	public boolean createSurvey(Survey survey, List<Question> questions, List<SurveyResult> surveyResults) {
		Survey createdSurvey = surveyRepository.save(survey);
		questionService.createQuestionInSurvey(createdSurvey.getId(), questions);
		surveyResultService.createSurveyResult(createdSurvey.getId(), surveyResults);

		return true;
	}

	public List<SurveyResponse> getRequesterSurveys(Long requesterId) {
		List<Survey> requesterSurveys = surveyRepository.findByRequesterId(requesterId);

		List<SurveyResponse> surveyResponses = new ArrayList<>();
		for (Survey requesterSurvey : requesterSurveys) {
			Long surveyId = requesterSurvey.getId();
			Long responserCount = surveyResultService.getResponserCount(surveyId);
			SurveyResponse surveyResponse = new SurveyResponse(requesterSurvey, responserCount);
			surveyResponses.add(surveyResponse);
		}

		return surveyResponses;
	}
}
