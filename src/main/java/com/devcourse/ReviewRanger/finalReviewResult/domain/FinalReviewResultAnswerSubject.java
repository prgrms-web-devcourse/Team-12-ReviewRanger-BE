package com.devcourse.ReviewRanger.finalReviewResult.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;

@Getter
@Entity
@Table(name = "answers_subject")
public class FinalReviewResultAnswerSubject extends FinalReviewResultAnswer {

	private String subjects;

	public FinalReviewResultAnswerSubject() {
		super();
	}

	public FinalReviewResultAnswerSubject(Long userId, Long questionId) {
		super(userId, questionId);
	}

	@Override
	public void addAnswer(Object answer) {
		this.subjects = String.valueOf(answer);
	}
}
