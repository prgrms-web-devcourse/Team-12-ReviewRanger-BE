package com.devcourse.ReviewRanger.finalReviewResult.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResultAnswerSubject;

public interface SubjectTypeRepository extends JpaRepository<FinalReviewResultAnswerSubject, Long> {
	Optional<FinalReviewResultAnswerSubject> findByQuestionIdAndUserId(Long questionId, Long userId);
}
