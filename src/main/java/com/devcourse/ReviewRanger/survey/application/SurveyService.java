package com.devcourse.ReviewRanger.survey.application;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.question.application.QuestionService;
import com.devcourse.ReviewRanger.question.domain.Question;
import com.devcourse.ReviewRanger.survey.domain.Survey;
import com.devcourse.ReviewRanger.survey.dto.response.SurveyResponse;
import com.devcourse.ReviewRanger.survey.repository.SurveyRepository;
import com.devcourse.ReviewRanger.surveyresult.application.SurveyResultService;
import com.devcourse.ReviewRanger.surveyresult.domain.DeadlineStatus;
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

		questions.forEach(question -> question.setSurvey(createdSurvey));
		questionService.createAllQuestions(questions);

		surveyResultService.createSurveyResult(createdSurvey.getId(), surveyResults);
		return true;
	}

	public List<SurveyResponse> getAllCreatedSurveysByRequester(Long requesterId) {
		List<Survey> surveys = surveyRepository.findByRequesterId(requesterId);

		List<SurveyResponse> surveyResponses = new ArrayList<>();
		for (Survey survey : surveys) {
			Long surveyId = survey.getId();
			Long responserCount = surveyResultService.getResponserCount(surveyId);
			SurveyResponse surveyResponse = new SurveyResponse(survey, responserCount);
			surveyResponses.add(surveyResponse);
		}

		return surveyResponses;
	}

	@Transactional
	public Boolean closeSurveyOrThrow(Long surveyId) {
		Survey survey = surveyRepository.findById(surveyId).orElseThrow(EntityNotFoundException::new);
		survey.changeStatus(DeadlineStatus.END);

		return surveyResultService.closeSurveyResultOrThrow(surveyId);
	}
}
