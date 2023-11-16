package com.devcourse.ReviewRanger.finalReviewResult.repository;

import static com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResult.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResult;

public interface FinalReviewResultRepository
	extends JpaRepository<FinalReviewResult, Long>, FinalReviewResultCustomRepository {
	List<FinalReviewResult> findAllByUserIdAndStatus(Long userId, Status status);

	List<FinalReviewResult> findAllByReviewIdAndStatus(Long reviewId, Status status);

	Optional<FinalReviewResult> findByReviewIdAndUserIdAndStatus(Long reviewId, Long userId, Status status);
}
