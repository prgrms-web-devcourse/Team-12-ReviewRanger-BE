package com.devcourse.ReviewRanger.review.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devcourse.ReviewRanger.participation.domain.ReviewStatus;
import com.devcourse.ReviewRanger.review.domain.Review;

public interface ReviewRepository extends
	JpaRepository<Review, Long>, ReviewCustomRepository {

	List<Review> findByRequesterId(Long requesterId);

	Optional<Review> findByTitleAndStatusOrderByCreateAtDesc(String title, ReviewStatus status);
}
