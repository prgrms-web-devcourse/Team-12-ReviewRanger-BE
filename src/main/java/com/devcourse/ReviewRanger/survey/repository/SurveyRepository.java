package com.devcourse.ReviewRanger.survey.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devcourse.ReviewRanger.survey.domain.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
}
