package com.devcourse.ReviewRanger.surveyresult.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devcourse.ReviewRanger.surveyresult.domain.SurveyResult;

public interface SurveyResultRepository extends JpaRepository<SurveyResult, Long> {

	List<SurveyResult> findByResponserId(Long responserId);

	SurveyResult findBySurveyIdAndResponserId(Long surveyId, Long responserId);
}
