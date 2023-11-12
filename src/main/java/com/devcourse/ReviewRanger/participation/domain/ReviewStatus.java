package com.devcourse.ReviewRanger.participation.domain;

import lombok.Getter;

@Getter
public enum ReviewStatus {
	PROCEEDING("진행중"),
	DEADLINE("마감"),
	END("종료");

	private final String displayName;

	ReviewStatus(String displayName) {
		this.displayName = displayName;
	}
}
