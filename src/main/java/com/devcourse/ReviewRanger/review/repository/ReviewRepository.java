package com.devcourse.ReviewRanger.review.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devcourse.ReviewRanger.participation.domain.ReviewStatus;
import com.devcourse.ReviewRanger.review.domain.Review;

public interface ReviewRepository extends
	JpaRepository<Review, Long>, ReviewCustomRepository {

	Optional<Review> findByIdAndStatus(Long id, ReviewStatus status);

	Optional<Review> findByTitleAndStatusOrderByCreateAtDesc(String title, ReviewStatus status);
}
