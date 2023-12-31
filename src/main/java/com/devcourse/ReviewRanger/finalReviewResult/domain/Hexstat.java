package com.devcourse.ReviewRanger.finalReviewResult.domain;

import javax.persistence.Column;

import lombok.Getter;

@Getter
public class Hexstat {

	@Column(name = "stat_name", nullable = false)
	private String statName;

	@Column(name = "stat_score", nullable = false)
	private Double statScore;

	protected Hexstat() {
	}

	public Hexstat(String statName, Double statScore) {
		this.statName = statName;
		this.statScore = statScore;
	}
}
