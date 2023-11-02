package com.devcourse.ReviewRanger.eachSurveyResult.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devcourse.ReviewRanger.eachSurveyResult.domain.EachSurveyResult;

public interface EachSurveyResultRepository extends JpaRepository<EachSurveyResult, Long> {
	List<EachSurveyResult> findBySurveyResultId(Long surveyResultId);
}
