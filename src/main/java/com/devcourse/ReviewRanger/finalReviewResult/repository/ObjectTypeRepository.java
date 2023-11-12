package com.devcourse.ReviewRanger.finalReviewResult.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResultAnswerObjects;

public interface ObjectTypeRepository extends JpaRepository<FinalReviewResultAnswerObjects, Long> {
}
