package com.devcourse.ReviewRanger.finalReviewResult.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResultAnswerRating;

public interface RatingTypeRepository extends JpaRepository<FinalReviewResultAnswerRating, Long> {
	List<FinalReviewResultAnswerRating> findAllByQuestionIdAndUserId(Long questionId, Long userId);
}
