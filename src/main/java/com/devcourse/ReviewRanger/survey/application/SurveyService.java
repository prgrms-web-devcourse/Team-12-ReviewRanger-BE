package com.devcourse.ReviewRanger.survey.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.question.application.QuestionService;
import com.devcourse.ReviewRanger.question.domain.Question;
import com.devcourse.ReviewRanger.survey.domain.Survey;
import com.devcourse.ReviewRanger.survey.repository.SurveyRepository;

@Service
@Transactional(readOnly = true)
public class SurveyService {

	private final QuestionService questionService;

	private final SurveyRepository surveyRepository;

	public SurveyService(QuestionService questionService, SurveyRepository surveyRepository) {
		this.questionService = questionService;
		this.surveyRepository = surveyRepository;
	}

	@Transactional
	public boolean createSurvey(Survey survey, List<Question> questions) {
		Survey save = surveyRepository.save(survey);
		questionService.createQuestionInSurvey(save.getId(), questions);
		
		return true;
	}
}
