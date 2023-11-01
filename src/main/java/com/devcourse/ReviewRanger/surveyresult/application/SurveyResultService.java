package com.devcourse.ReviewRanger.surveyresult.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.surveyresult.domain.SurveyResult;
import com.devcourse.ReviewRanger.surveyresult.repository.SurveyResultRepository;

@Service
@Transactional(readOnly = true)
public class SurveyResultService {

	private final SurveyResultRepository surveyResultRepository;

	public SurveyResultService(SurveyResultRepository surveyResultRepository) {
		this.surveyResultRepository = surveyResultRepository;
	}

	public SurveyResult findSurveyResult(Long surveyId, Long responserId) {
		return surveyResultRepository.findBySurveyIdAndResponserId(surveyId, responserId);
	}
}
