package com.devcourse.ReviewRanger.finalReviewResult.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;

@Getter
@Entity
@Table(name = "answers_object")
public class FinalReviewResultAnswerObjects extends FinalReviewResultAnswer {

	private String object;

	public FinalReviewResultAnswerObjects() {
		super();
	}

	public FinalReviewResultAnswerObjects(Long questionId) {
		super(questionId);
	}

	@Override
	public void addAnswer(Object answer) {
		this.object = String.valueOf(answer);
	}
}
