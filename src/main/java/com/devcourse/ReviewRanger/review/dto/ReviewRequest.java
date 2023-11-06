package com.devcourse.ReviewRanger.review.dto;

import java.time.LocalDateTime;

import com.devcourse.ReviewRanger.review.domain.Review;
import com.devcourse.ReviewRanger.review.domain.ReviewType;

public record ReviewRequest(String title, String description, ReviewType reviewType, LocalDateTime closedAt) {

	public Review toEntity() {
		return new Review(title, description, reviewType, closedAt);
	}
}
