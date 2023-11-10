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

	@Column(name = "receiver_id", nullable = false)
	private Long receiverId;

	@Column(name = "participation_id", nullable = false)
	private Long participationId;

	@OneToMany(mappedBy = "reviewedTarget", orphanRemoval = true)
	private List<Reply> replies = new ArrayList<>();

	protected ReviewedTarget() {
	}

	public ReviewedTarget(Long receiverId) {
		this.receiverId = receiverId;
	}

	public void setParticipationId(Long participationId) {
		this.participationId = participationId;
	}

	public ReviewedTarget(Long receiverId, Long participationId) {
		this.receiverId = receiverId;
		this.participationId = participationId;
	}
}
