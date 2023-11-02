package com.devcourse.ReviewRanger.surveyresult.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devcourse.ReviewRanger.surveyresult.domain.SurveyResult;

public interface SurveyResultRepository extends JpaRepository<SurveyResult, Long> {

	List<SurveyResult> findByResponserId(Long responserId);

	List<SurveyResult> findBySurveyId(Long surveyId);

	SurveyResult findBySurveyIdAndResponserId(Long surveyId, Long responserId);

	List<SurveyResult> findBySurveyIdAndQuestionAnsweredStatusTrue(Long surveyId);
}
