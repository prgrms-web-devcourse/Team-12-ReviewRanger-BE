package com.devcourse.ReviewRanger.question.domain;

import com.devcourse.ReviewRanger.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@Entity
@Table(name = "question_options")
public class QuestionOption extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "question_id")
	private Question question;

	@Column(name = "option_context", nullable = false, length = 100)
	@NotBlank(message = "옵션 내용은 빈값 일 수 없습니다.")
	@Size(max = 100, message = "150자 이하로 입력하세요.")
	private String optionContext;

	protected QuestionOption() {
	}

	public QuestionOption(String optionContext) {
		this.optionContext = optionContext;
	}

}
