package com.devcourse.ReviewRanger.participation.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.devcourse.ReviewRanger.BaseEntity;

import lombok.Getter;

@Getter
@Entity
@Table(name = "participations")
public class Participation extends BaseEntity {

	@Column(name = "survey_id", nullable = false)
	private Long reviewId;

	@Column(name = "responser_id", nullable = false)
	private Long responserId;

	@Column(name = "deadline_status", nullable = false)
	@Enumerated(EnumType.STRING)
	private DeadlineStatus deadlineStatus;

	@Column(name = "question_answered_status", nullable = false)
	private Boolean questionAnsweredStatus = false;

	protected Participation() {
	}

	public Participation(Long responserId) {
		this.responserId = responserId;
		this.deadlineStatus = DeadlineStatus.PROCEEDING;
		this.questionAnsweredStatus = false;
	}

	public void assignReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}

	public void changeStatus(DeadlineStatus status) {
		this.deadlineStatus = status;
	}
}
