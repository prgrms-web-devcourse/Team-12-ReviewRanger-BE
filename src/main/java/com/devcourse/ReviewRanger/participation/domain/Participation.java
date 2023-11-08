package com.devcourse.ReviewRanger.participation.domain;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.*;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.devcourse.ReviewRanger.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

@Getter
@Entity
@Table(name = "participations")
public class Participation extends BaseEntity {

	@Column(name = "review_id", nullable = false)
	private Long reviewId;

	@Column(name = "responser_id", nullable = false)
	private Long responserId;

	@Column(name = "deadline_status", nullable = false)
	@Enumerated(EnumType.STRING)
	private DeadlineStatus deadlineStatus;

	@Column(name = "submit_At")
	@JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime submitAt;

	protected Participation() {
	}

	public Participation(Long responserId) {
		this.responserId = responserId;
		this.deadlineStatus = DeadlineStatus.PROCEEDING;
	}

	public void assignReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}

	public void changeStatus(DeadlineStatus status) {
		this.deadlineStatus = status;
	}
}
