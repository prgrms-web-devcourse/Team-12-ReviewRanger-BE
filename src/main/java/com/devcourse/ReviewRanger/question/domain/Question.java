package com.devcourse.ReviewRanger.question.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.devcourse.ReviewRanger.BaseEntity;

import lombok.Getter;

@Getter
@Entity
@Table(name = "questions")
public class Question extends BaseEntity {

	@Column(name = "review_id", nullable = false)
	private Long reviewId;

	@Column(nullable = false, length = 150)
	@NotBlank(message = "질문제목은 빈값 일 수 없습니다.")
	@Size(max = 150, message = "150자 이하로 입력하세요.")
	private String title;

	@Column(length = 500)
	@Size(max = 500, message = "150자 이하로 입력하세요.")
	private String description;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private QuestionType type;

	@Column(name = "is_required", nullable = false)
	private Boolean isRequired;

	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
	private List<QuestionOption> questionOptions = new ArrayList<>();

	protected Question() {
	}

	public Question(String title, String description,  QuestionType type, Boolean isRequired, List<QuestionOption> questionOptions) {
		this.title = title;
		this.description = description;
		this.type = type;
		this.isRequired = isRequired;
		this.questionOptions = questionOptions;
	}

	public Question(String title, String description, QuestionType type, Boolean isRequired) {
		this.title = title;
		this.description = description;
		this.type = type;
		this.isRequired = isRequired;
	}

	public void assignReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}
}
