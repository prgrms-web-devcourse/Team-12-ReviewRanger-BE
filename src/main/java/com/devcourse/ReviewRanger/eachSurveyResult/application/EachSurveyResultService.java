package com.devcourse.ReviewRanger.eachSurveyResult.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.eachSurveyResult.domain.EachSurveyResult;
import com.devcourse.ReviewRanger.eachSurveyResult.repository.EachSurveyResultRepository;

@Service
@Transactional(readOnly = true)
public class EachSurveyResultService {

	private final EachSurveyResultRepository eachSurveyResultRepository;

	public EachSurveyResultService(EachSurveyResultRepository eachSurveyResultRepository) {
		this.eachSurveyResultRepository = eachSurveyResultRepository;
	}

	@Transactional
	public Long createEachSurveyResult(EachSurveyResult eachSurveyResult) {
		return eachSurveyResultRepository.save(eachSurveyResult).getId();
	}
}
