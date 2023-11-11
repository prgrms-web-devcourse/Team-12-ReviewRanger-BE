package com.devcourse.ReviewRanger.finalReviewResult.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "answers_dropdown")
public class FinalReviewResultAnswerDropdown extends FinalReviewResultAnswer {

	private String drops;

	public FinalReviewResultAnswerDropdown() {
		super();
	}

	public FinalReviewResultAnswerDropdown(Long questionId) {
		super(questionId);
	}

	@Override
	public void addAnswer(Object answer) {
		this.drops = String.valueOf(answer);
	}
}
