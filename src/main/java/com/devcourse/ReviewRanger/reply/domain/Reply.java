package com.devcourse.ReviewRanger.reply.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.devcourse.ReviewRanger.BaseEntity;
import com.devcourse.ReviewRanger.ReplyTarget.domain.ReplyTarget;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;

@Getter
@Entity
@Table(name = "replies")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
public class Reply extends BaseEntity {

	@Column(name = "question_id", nullable = false)
	private Long questionId;

	@Column(name = "object_option_id", nullable = true)
	private Long objectOptionId;

	@Lob
	@Column(name = "answer_text", nullable = true)
	private String answerText;

	@ManyToOne
	@JoinColumn(name = "reply_target_id")
	private ReplyTarget replyTarget;

	protected Reply() {
	}

	public Reply(Long questionId, Long objectOptionId, String answerText) {
		this.questionId = questionId;
		this.objectOptionId = objectOptionId;
		this.answerText = answerText;
	}

	public void assignReviewedTarget(ReplyTarget replyTarget) {
		if (this.replyTarget != null) {
			this.replyTarget.getReplies().remove(this);
		}
		this.replyTarget = replyTarget;
		replyTarget.getReplies().add(this);
	}

	public void update(Long objectOptionId, String answerText) {
		this.objectOptionId = objectOptionId;
		this.answerText = answerText;
	}
}
