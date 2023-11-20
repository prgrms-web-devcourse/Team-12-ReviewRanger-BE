package com.devcourse.ReviewRanger.review;

import static com.devcourse.ReviewRanger.review.domain.ReviewType.*;

import com.devcourse.ReviewRanger.review.domain.Review;
import com.devcourse.ReviewRanger.review.domain.ReviewType;

public enum ReviewFixture {

	BASIC_REVIEW("예시 리뷰", "예시 리뷰 설명입니다.", PEER_REVIEW);

	private final String title;
	private final String description;
	private final ReviewType type ;

	ReviewFixture(String title, String description, ReviewType type) {
		this.title = title;
		this.description = description;
		this.type = type;
	}

	public Review toEntity(){
		return new Review(title, description, type);
	}
}
