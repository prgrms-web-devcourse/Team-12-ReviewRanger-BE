package com.devcourse.ReviewRanger.afterresult.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.devcourse.ReviewRanger.BaseEntity;
import com.devcourse.ReviewRanger.question.domain.QuestionType;

@Entity
@Table(name = "after_responses")
public class AfterResponse extends BaseEntity {

	@Column(name = "after_result_id", nullable = false)
	@NotBlank(message = "조합된 결과 id는 빈값 일 수 없습니다.")
	private Long afterResultId;

	@Column(name = "question_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private QuestionType questionType;

	@Column(name = "question_title", nullable = false)
	@NotBlank(message = "질문제목은 빈값 일 수 없습니다.")
	@Size(max = 150, message = "150자 이하로 입력하세요.")
	private String questionTitle;

	@Lob
	@Column(nullable = false)
	private String content;

	@Column(nullable = false)
	private Integer sequence;
}
