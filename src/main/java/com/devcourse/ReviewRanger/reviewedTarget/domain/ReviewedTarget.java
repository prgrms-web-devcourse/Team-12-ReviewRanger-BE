package com.devcourse.ReviewRanger.reviewedTarget.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.devcourse.ReviewRanger.BaseEntity;
import com.devcourse.ReviewRanger.reply.domain.Reply;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;

@Getter
@Entity
@Table(name = "reviewed_targets")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
public class ReviewedTarget extends BaseEntity {

	@Column(name = "subject_id", nullable = false)
	private Long subjectId;

	@Column(name = "participation_id", nullable = false)
	private Long participationId;

	@OneToMany(mappedBy = "reviewedTarget", orphanRemoval = true)
	private List<Reply> replies = new ArrayList<>();

	protected ReviewedTarget() {
	}

	public ReviewedTarget(Long subjectId) {
		this.subjectId = subjectId;
	}

	public void setParticipationId(Long participationId) {
		this.participationId = participationId;
	}

	public ReviewedTarget(Long subjectId, Long participationId) {
		this.subjectId = subjectId;
		this.participationId = participationId;
	}
}
