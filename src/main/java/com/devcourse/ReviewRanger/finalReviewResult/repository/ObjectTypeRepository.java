package com.devcourse.ReviewRanger.finalReviewResult.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResultAnswerObjects;

public interface ObjectTypeRepository extends JpaRepository<FinalReviewResultAnswerObjects, Long> {
	List<FinalReviewResultAnswerObjects> findAllByQuestionId(Long questionId);
}
