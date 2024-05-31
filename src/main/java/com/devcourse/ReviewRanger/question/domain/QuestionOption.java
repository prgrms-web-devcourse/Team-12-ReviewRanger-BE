package com.devcourse.ReviewRanger.question.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.devcourse.ReviewRanger.common.entity.BaseEntity;

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
	private String optionName;

	protected QuestionOption() {
	}

	public QuestionOption(String optionName) {
		this.optionName = optionName;
	}

	public void assignQuestion(Question question) {
		this.question = question;
	}
}
