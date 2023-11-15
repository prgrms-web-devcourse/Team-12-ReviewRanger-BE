package com.devcourse.ReviewRanger.finalReviewResult.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;

@Getter
@Embeddable
public class FinalQuestion {

	@Column(name = "question_id", nullable = false)
	private Long questionId;

	@Column(name = "question_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private FinalQuestionType questionType;

	@Column(name = "question_title", nullable = false)
	@NotBlank(message = "질문 제목은 빈값 일 수 없습니다.")
	@Size(max = 150, message = "150자 이하로 입력하세요.")
	private String questionTitle;

	protected FinalQuestion() {
	}

	public FinalQuestion(Long questionId, FinalQuestionType questionType, String questionTitle) {
		this.questionId = questionId;
		this.questionType = questionType;
		this.questionTitle = questionTitle;
	}
}
