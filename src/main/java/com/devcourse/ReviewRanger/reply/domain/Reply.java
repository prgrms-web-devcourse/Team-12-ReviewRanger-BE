package com.devcourse.ReviewRanger.reply.domain;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.devcourse.ReviewRanger.BaseEntity;
import com.devcourse.ReviewRanger.ReplyTarget.domain.ReplyTarget;
import com.devcourse.ReviewRanger.common.exception.RangerException;
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

	@Column(name = "question_option_id", nullable = true)
	private Long questionOptionId;

	@Lob
	@Column(name = "answer_text", nullable = true)
	private String answerText;

	@Column(name = "rating", nullable = true)
	private Double rating;

	@Column(name = "hexastat", nullable = true)
	private Integer hexastat;

	@ManyToOne
	@JoinColumn(name = "reply_target_id")
	private ReplyTarget replyTarget;

	protected Reply() {
	}

	public Reply(Long questionId, Long questionOptionId, String answerText) {
		this.questionId = questionId;
		this.questionOptionId = questionOptionId;
		this.answerText = answerText;
	}

	public Reply(Long questionId, Long questionOptionId, String answerText, Double rating, Integer hexastat) {
		this.questionId = questionId;
		this.questionOptionId = questionOptionId;
		this.answerText = answerText;
		this.rating = rating;
		this.hexastat = hexastat;
	}

	public void assignReviewedTarget(ReplyTarget replyTarget) {
		if (this.replyTarget != null) {
			this.replyTarget.getReplies().remove(this);
		}
		this.replyTarget = replyTarget;
		replyTarget.getReplies().add(this);
	}

	public void update(Long questionOptionId, String answerText, Double rating, Integer hexastat) {
		this.questionOptionId = questionOptionId;
		this.answerText = answerText;
		this.rating = rating;
		this.hexastat = hexastat;
	}

	public void validateReplyInputsOrThrow() {
		if (this.answerText == null && this.questionOptionId == null && this.rating == null && this.hexastat == null) {
			throw new RangerException(MISSING_REQUIRED_QUESTION_REPLY);
		}
	}
}
