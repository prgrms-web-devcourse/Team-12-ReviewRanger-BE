package com.devcourse.ReviewRanger.survey.domain;

public enum SurveyStatus {
	PROCEEDING("진행"),
	DEADLINE("마감"),
	END("종료")
	;

	private final String displayName;

	SurveyStatus(String displayName) {
		this.displayName = displayName;
	}
}
