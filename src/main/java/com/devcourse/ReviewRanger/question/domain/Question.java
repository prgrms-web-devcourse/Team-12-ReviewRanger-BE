package com.devcourse.ReviewRanger.question.domain;

import java.util.ArrayList;
import java.util.List;

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

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private QuestionType type;

	@Column(nullable = false)
	private Integer sequence;

	@Column(name = "is_required", nullable = false)
	private Boolean isRequired;

	@Column(name = "is_duplicated", nullable = false)
	private Boolean isDuplicated;

	@OneToMany(mappedBy = "question")
	private List<QuestionOption> questionOptions = new ArrayList<>();

	protected Question() {
	}

	public Question(String title, QuestionType type, Integer sequence, Boolean isRequired, boolean isDuplicated,
		List<QuestionOption> questionOptions) {
		this.title = title;
		this.type = type;
		this.sequence = sequence;
		this.isRequired = isRequired;
		this.isDuplicated = isDuplicated;
		this.questionOptions = questionOptions;
	}

	public void assignReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}
}
