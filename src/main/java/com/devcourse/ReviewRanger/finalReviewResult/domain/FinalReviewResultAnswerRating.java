package com.devcourse.ReviewRanger.finalReviewResult.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;

@Getter
@Entity
@Table(name = "answers_rating")
public class FinalReviewResultAnswerRating extends FinalReviewResultAnswer {

	private Double rate;

	public FinalReviewResultAnswerRating() {
		super();
	}

	public FinalReviewResultAnswerRating(Long userId, Long questionId) {
		super(userId, questionId);
	}

	@Override
	public void addAnswer(Object answer) {
		ObjectMapper mapper = new ObjectMapper();
		Double rate = mapper.convertValue(answer, Double.class);

		this.rate = rate;
	}
}
