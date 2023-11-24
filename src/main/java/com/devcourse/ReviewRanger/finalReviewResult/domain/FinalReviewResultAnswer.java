package com.devcourse.ReviewRanger.finalReviewResult.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class FinalReviewResultAnswer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "question_id")
	private Long questionId;

	protected FinalReviewResultAnswer() {
	}

	protected FinalReviewResultAnswer(Long userId, Long questionId) {
		this.userId = userId;
		this.questionId = questionId;
	}

	public abstract void addAnswer(Object answer);
}
