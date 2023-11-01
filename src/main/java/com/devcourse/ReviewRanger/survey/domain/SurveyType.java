package com.devcourse.ReviewRanger.survey.domain;

import lombok.Getter;

@Getter
public enum SurveyType {
	PEER_REVIEW("피어 리뷰")
	;

	private final String displayName;

	SurveyType(String displayName) {
		this.displayName = displayName;
	}
}
