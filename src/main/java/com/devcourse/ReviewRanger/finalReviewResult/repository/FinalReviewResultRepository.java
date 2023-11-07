package com.devcourse.ReviewRanger.finalReviewResult.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResult;

public interface FinalReviewResultRepository extends JpaRepository<FinalReviewResult, Long> {
	List<FinalReviewResult> findAllByUserId(Long userId);
}
