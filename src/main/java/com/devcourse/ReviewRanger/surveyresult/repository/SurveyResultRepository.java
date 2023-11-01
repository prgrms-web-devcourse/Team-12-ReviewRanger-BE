package com.devcourse.ReviewRanger.surveyresult.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devcourse.ReviewRanger.surveyresult.domain.SurveyResult;

public interface SurveyResultRepository extends JpaRepository<SurveyResult, Long> {
}
