package com.devcourse.ReviewRanger.reviewedTarget.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.devcourse.ReviewRanger.BaseEntity;

import lombok.Getter;

@Getter
@Entity
@Table(name = "reviewed_targets")
public class ReviewedTarget extends BaseEntity {

	@Column(name = "subject_id", nullable = false)
	private Long subjectId;

	@Column(name = "participation_id", nullable = false)
	private Long participationId;

	protected ReviewedTarget() {
	}

	public ReviewedTarget(Long subjectId, Long participationId) {
		this.subjectId = subjectId;
		this.participationId = participationId;
	}
}
