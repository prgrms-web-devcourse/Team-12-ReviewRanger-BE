package com.devcourse.ReviewRanger.common.constant;

import lombok.Getter;

@Getter
public enum Status {
	PROCEEDING("진행중"),
	DEADLINE("제출"),
	END("마감")
	;

	private final String displayName;

	Status(String displayName) {
		this.displayName = displayName;
	}
}
