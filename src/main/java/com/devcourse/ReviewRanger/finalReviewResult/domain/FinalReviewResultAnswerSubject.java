package com.devcourse.ReviewRanger.finalReviewResult.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "answers_subject")
public class FinalReviewResultAnswerSubject extends FinalReviewResultAnswer {

	private String subjects;

	public FinalReviewResultAnswerSubject() {
		super();
	}

	public FinalReviewResultAnswerSubject(Long questionId) {
		super(questionId);
	}

	@Override
	public void addAnswer(Object answer) {
		this.subjects = String.valueOf(answer);
	}
}