package com.devcourse.ReviewRanger.reply.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.devcourse.ReviewRanger.BaseEntity;
import com.devcourse.ReviewRanger.reviewedTarget.domain.ReviewedTarget;

import lombok.Getter;

@Getter
@Entity
@Table(name = "replies")
public class Reply extends BaseEntity {

	@Column(name = "responser_id", nullable = false)
	private Long responserId;

	@Column(name = "question_id", nullable = false)
	private Long questionId;

	@Column(name = "option_id", nullable = true)
	private Integer optionId;

	@Lob
	@Column(name = "answer_text", nullable = true)
	private String answerText;

	@ManyToOne
	@JoinColumn(name = "reviewed_target_id")
	private ReviewedTarget reviewedTarget;

	protected Reply() {
	}

	public Reply(Long responserId, Long questionId, Integer optionId, String answerText) {
		this.responserId = responserId;
		this.questionId = questionId;
		this.optionId = optionId;
		this.answerText = answerText;
	}

	public void setReviewedTarget(ReviewedTarget reviewedTarget) {
		this.reviewedTarget = reviewedTarget;
	}
}
