package com.devcourse.ReviewRanger.finalReviewResult.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResultAnswerSubject;

public interface SubjectTypeRepository extends JpaRepository<FinalReviewResultAnswerSubject, Long> {
	List<FinalReviewResultAnswerSubject> findAllByQuestionId(Long questionId);

	Optional<FinalReviewResultAnswerSubject> findByQuestionIdAndUserId(Long questionId, Long userId);
}
