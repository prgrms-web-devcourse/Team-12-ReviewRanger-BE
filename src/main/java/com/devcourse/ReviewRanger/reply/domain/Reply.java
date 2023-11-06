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

	@Lob
	@Column(name = "answer_text", nullable = true)
	private String answerText;

	@ManyToOne
	@JoinColumn(name = "reviewed_target_id")
	private ReviewedTarget reviewedTarget;

	protected Reply() {
	}

	public Reply(Long responserId, ReviewedTarget reviewedTarget, Long questionId) {
		this.responserId = responserId;
		this.reviewedTarget = reviewedTarget;
		this.questionId = questionId;
	}

	public Reply(Long responserId, ReviewedTarget reviewedTarget, Long questionId, String answerText) {
		this(responserId, reviewedTarget, questionId);
		this.answerText = answerText;
	}
}
