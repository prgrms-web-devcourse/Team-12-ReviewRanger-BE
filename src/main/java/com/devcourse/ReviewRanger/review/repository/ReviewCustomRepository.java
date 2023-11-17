package com.devcourse.ReviewRanger.review.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.devcourse.ReviewRanger.review.domain.Review;

public interface ReviewCustomRepository {

	Slice<Review> findByRequesterId(Long cursorId, Long requesterId, Pageable pageable);
}
