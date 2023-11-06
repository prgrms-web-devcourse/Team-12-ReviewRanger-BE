package com.devcourse.ReviewRanger.review.domain;

import lombok.Getter;

@Getter
public enum ReviewType {
	PEER_REVIEW("피어 리뷰");

	private final String displayName;

	ReviewType(String displayName) {
		this.displayName = displayName;
	}
}
