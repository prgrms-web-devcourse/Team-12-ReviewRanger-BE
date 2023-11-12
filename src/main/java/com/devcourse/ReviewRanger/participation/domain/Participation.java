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

	@Column(name = "is_answered", nullable = false)
	private Boolean isAnswered;

	@Column(name = "review_status", nullable = false)
	@Enumerated(EnumType.STRING)
	private ReviewStatus reviewStatus;

	@Column(name = "submit_At")
	@JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime submitAt;

	protected Participation() {
	}

	public Participation(Long responserId) {
		this.responserId = responserId;
		this.isAnswered = false;
		this.reviewStatus = ReviewStatus.PROCEEDING;
	}

	public void assignReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}

	public void changeStatus(ReviewStatus status) {
		this.reviewStatus = status;
	}

	public void answeredReview() {
		this.submitAt = LocalDateTime.now();
		this.isAnswered = true;
	}
}
