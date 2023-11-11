package com.devcourse.ReviewRanger.finalReviewResult.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResultAnswerSubject;

public interface SubjectTypeRepository extends JpaRepository<FinalReviewResultAnswerSubject, Long> {
}