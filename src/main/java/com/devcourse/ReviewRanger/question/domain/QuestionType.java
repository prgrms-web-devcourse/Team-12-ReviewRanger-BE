package com.devcourse.ReviewRanger.question.domain;

import lombok.Getter;

@Getter
public enum QuestionType {
	SUBJECTIVE("주관식"),
	OBJECTIVE_UNIQUE("객관식_중복없음"),
	OBJECTIVE_DUPLICATE("객관식_중복있음"),
	RATING("별점"),
	DROPDOWN("드롭다운"),
	HEXASTAT("육각스텟")
	;

	private final String displayName;

	QuestionType(String displayName) {
		this.displayName = displayName;
	}
}
