package com.devcourse.ReviewRanger.surveyresult.application;


import java.util.List;

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

	@Transactional
	public void createSurveyResult(Long surveyId, List<SurveyResult> surveyResults) {
		surveyResults.forEach(surveyResult -> surveyResult.assignSurveyId(surveyId));
		surveyResultRepository.saveAll(surveyResults);
	}

	public List<SurveyResult> getResponserSurveyResult(Long responserId) {
		return surveyResultRepository.findByResponserId(responserId);
  }
  
	public SurveyResult findSurveyResult(Long surveyId, Long responserId) {
		return surveyResultRepository.findBySurveyIdAndResponserId(surveyId, responserId);
	}
}
