package com.devcourse.ReviewRanger.question.domain;

import com.devcourse.ReviewRanger.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Entity
@Table(name = "question_options")
public class QuestionOption extends BaseEntity {

	@Column(name = "question_id", nullable = false)
	Long questionId;

	@Column(name = "option_context", nullable = false)
	@NotBlank(message = "옵션 내용은 빈값 일 수 없습니다.")
	String optionContext;

	protected QuestionOption() {
	}

	public QuestionOption(Long questionId, String optionContext) {
		this.questionId = questionId;
		this.optionContext = optionContext;
	}
}
