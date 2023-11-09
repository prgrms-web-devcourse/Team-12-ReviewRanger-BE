package com.devcourse.ReviewRanger.finalReviewResult.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.MapKeyColumn;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.devcourse.ReviewRanger.BaseEntity;
import com.devcourse.ReviewRanger.question.domain.QuestionType;

// @Embeddable
@Entity
@Table(name = "final_review_replies")
public class FinalReviewResultReply extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "final_review_result_id")
	private FinalReviewResult finalReviewResult;

	@Column(name = "question_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private QuestionType questionType;

	@Column(name = "question_title", nullable = false)
	@NotBlank(message = "질문 제목은 빈값 일 수 없습니다.")
	@Size(max = 150, message = "150자 이하로 입력하세요.")
	private String questionTitle;

	@ElementCollection
	@CollectionTable(name = "subjects")
	@OrderColumn(name = "subject_index")
	private List<String> subjects = new ArrayList<>();

	@ElementCollection
	@CollectionTable(name = "objects")
	@OrderColumn(name = "object_index")
	private List<String> objects = new ArrayList<>();

	@ElementCollection
	@CollectionTable(name = "ratings")
	private List<Integer> ratings = new ArrayList<>();

	@ElementCollection
	@CollectionTable(name = "hexstats")
	private List<Hexstat> hexStats = new ArrayList<>();

	public void assignFinalReviewResult(FinalReviewResult finalReviewResult) {
		this.finalReviewResult = finalReviewResult;
	}
}
