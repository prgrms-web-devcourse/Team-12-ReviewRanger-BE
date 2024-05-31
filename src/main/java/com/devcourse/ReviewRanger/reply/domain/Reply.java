package com.devcourse.ReviewRanger.reply.domain;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;

import com.devcourse.ReviewRanger.common.entity.BaseEntity;
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
	@DecimalMax(value = "5.0", message = "별점은 1~5까지 값만 가능합니다.")
	private Double rating;

	@Column(name = "hexastat", nullable = true)
	@Max(value = 10, message = "헥사 스탯은 1~10까지 값만 가능합니다.")
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
		if (isEmptyAnswer()) {
			throw new RangerException(MISSING_REQUIRED_QUESTION_REPLY);
		}
	}

	public boolean isNotOptionQuestionReply() {
		return this.questionOptionId == null || this.questionOptionId == 0;
	}

	public boolean isEmptyAnswer() {
		return isEmpty(this.answerText) || isEmpty(this.questionOptionId) || isEmpty(this.rating) || isEmpty(
			this.hexastat);
	}

	private boolean isEmpty(String str) {
		return str != null && str.isEmpty();
	}

	private boolean isEmpty(Long longValue) {
		return longValue != null && longValue.longValue() == 0;
	}

	private boolean isEmpty(Double doubleValue) {
		return doubleValue != null && doubleValue.doubleValue() == 0;
	}

	private boolean isEmpty(Integer integerValue) {
		return integerValue != null && integerValue.intValue() == 0;
	}
}
