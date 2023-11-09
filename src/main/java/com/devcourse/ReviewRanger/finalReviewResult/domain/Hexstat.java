package com.devcourse.ReviewRanger.finalReviewResult.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Hexstat {

	@Column(name = "stat_name", nullable = false)
	private String statName;

	@Column(name = "stat_score", nullable = false)
	private Integer statScore;
}
