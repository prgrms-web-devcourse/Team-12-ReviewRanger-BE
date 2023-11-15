package com.devcourse.ReviewRanger.finalReviewResult.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResultAnswerDropdown;

public interface DropdownTypeRepository extends JpaRepository<FinalReviewResultAnswerDropdown, Long> {
	List<FinalReviewResultAnswerDropdown> findAllByQuestionId(Long questionId);
}
