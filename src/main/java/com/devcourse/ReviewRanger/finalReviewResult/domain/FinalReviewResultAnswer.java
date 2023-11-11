package com.devcourse.ReviewRanger.finalReviewResult.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Setter;

@MappedSuperclass
@Setter
public abstract class FinalReviewResultAnswer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "question_id")
	private Long questionId;

	protected FinalReviewResultAnswer() {
	}

	protected FinalReviewResultAnswer(Long questionId) {
		this.questionId = questionId;
	}

	public abstract void addAnswer(Object answer);
}