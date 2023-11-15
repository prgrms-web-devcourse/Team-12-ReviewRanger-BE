package com.devcourse.ReviewRanger.finalReviewResult.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;

@Getter
@Entity
@Table(name = "answers_hexstats")
public class FinalReviewResultAnswerHexStat extends FinalReviewResultAnswer {

	@Column(name = "stat_name", nullable = false)
	private String statName;

	@Column(name = "stat_score", nullable = false)
	private Double statScore;

	public FinalReviewResultAnswerHexStat() {
		super();
	}

	public FinalReviewResultAnswerHexStat(Long questionId) {
		super(questionId);
	}

	@Override
	public void addAnswer(Object answer) {
		ObjectMapper mapper = new ObjectMapper();
		Hexstat hexstat = mapper.convertValue(answer, Hexstat.class);

		this.statName = hexstat.getStatName();
		this.statScore = hexstat.getStatScore();
	}
}
