package com.devcourse.ReviewRanger.response.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devcourse.ReviewRanger.response.domain.EachSurveyResult;

public interface EachSurveyResultRepository extends JpaRepository<EachSurveyResult, Long> {
}
