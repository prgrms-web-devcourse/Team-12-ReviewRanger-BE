package com.devcourse.ReviewRanger.question.domain;

import com.devcourse.ReviewRanger.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Entity
@Table(name = "questions")
public class Question extends BaseEntity {

	@Column(nullable = false)
	Long surveyId;

	@Column(nullable = false)
	@NotBlank(message = "질문제목은 빈값 일 수 없습니다.")
	String title;

	@Column(nullable = false)
	QuestionType type;

	@Column(nullable = false)
	int sequence;

	@Column(nullable = false)
	boolean isRequired;

	@Column(nullable = false)
	boolean isDuplicated;

	protected Question() {
	}

	public Question(String title, QuestionType type, String options, int sequence, boolean isRequired,
		boolean isDuplicated) {
		this.title = title;
		this.type = type;
		this.sequence = sequence;
		this.isRequired = isRequired;
		this.isDuplicated = isDuplicated;
	}

	public void assignSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}
}
