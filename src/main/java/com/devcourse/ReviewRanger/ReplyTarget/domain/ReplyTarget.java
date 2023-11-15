package com.devcourse.ReviewRanger.ReplyTarget.domain;

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
@Table(name = "reply_targets")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
public class ReplyTarget extends BaseEntity {

	@Column(name = "receiver_id", nullable = false)
	private Long receiverId;

	@Column(name = "responser_id", nullable = false)
	private Long responserId;

	@Column(name = "participation_id", nullable = false)
	private Long participationId;

	@OneToMany(mappedBy = "replyTarget", orphanRemoval = true)
	private List<Reply> replies = new ArrayList<>();

	protected ReplyTarget() {
	}

	public ReplyTarget(Long receiverId, Long responserId) {
		this.receiverId = receiverId;
		this.responserId = responserId;
	}

	public void setParticipationId(Long participationId) {
		this.participationId = participationId;
	}
}
