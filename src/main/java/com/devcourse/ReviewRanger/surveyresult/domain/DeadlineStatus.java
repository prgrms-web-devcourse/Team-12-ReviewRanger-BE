package com.devcourse.ReviewRanger.surveyresult.domain;

import lombok.Getter;

@Getter
public enum DeadlineStatus {
	PROCEEDING("진행중"),
	DEADLINE("제출"),
	END("마감")
	;

	private final String displayName;

	DeadlineStatus(String displayName) {
		this.displayName = displayName;
	}
}
