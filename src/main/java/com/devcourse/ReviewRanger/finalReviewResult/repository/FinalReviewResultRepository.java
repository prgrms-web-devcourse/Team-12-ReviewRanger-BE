package com.devcourse.ReviewRanger.finalReviewResult.repository;

import static com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResult.*;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResult;

public interface FinalReviewResultRepository extends JpaRepository<FinalReviewResult, Long> {
	List<FinalReviewResult> findAllByUserIdAndStatus(Long userId, Status status);
}
