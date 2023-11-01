package com.devcourse.ReviewRanger.question.domain;

import java.util.Arrays;
import java.util.List;

import com.devcourse.ReviewRanger.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@Entity
@Table(name = "questions")
public class Question extends BaseEntity {

	@Column(name = "survey_id", nullable = false)
	@NotBlank(message = "설문지 Id는 빈값 일 수 없습니다.")
	private Long surveyId;

	@Column(nullable = false, length = 150)
	@NotBlank(message = "질문제목은 빈값 일 수 없습니다.")
	@Size(max = 150, message = "150자 이하로 입력하세요.")
	private String title;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private QuestionType type;

	@Column(nullable = false)
	private Integer sequence;

	@Column(name = "is_required", nullable = false)
	private Boolean isRequired;

	@Column(name = "is_duplicated", nullable = false)
	private Boolean isDuplicated;

	@Transient
	String options;

	protected Question() {
	}

	public Question(String title, QuestionType type, Integer sequence, Boolean isRequired,
		boolean isDuplicated, String options) {
		this.title = title;
		this.type = type;
		this.sequence = sequence;
		this.isRequired = isRequired;
		this.isDuplicated = isDuplicated;
	}

	public void assignSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}

	public boolean isAnswerDuplicated() {
		return this.isDuplicated;
	}

	public List<QuestionOption> createQuestionOptions() {
		return Arrays.stream(options.split(","))
			.map(optionContext -> new QuestionOption(optionContext))
			.toList();
	}
}
