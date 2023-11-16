package com.devcourse.ReviewRanger.ReplyTarget.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.devcourse.ReviewRanger.BaseEntity;
import com.devcourse.ReviewRanger.reply.domain.Reply;
import com.devcourse.ReviewRanger.user.domain.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;

@Getter
@Entity
@Table(name = "reply_targets")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
public class ReplyTarget extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receiver_id", nullable = false)
	private User receiver;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "responser_id", nullable = false)
	private User responser;

	@Column(name = "participation_id", nullable = false)
	private Long participationId;

	@OneToMany(mappedBy = "replyTarget", orphanRemoval = true)
	private List<Reply> replies = new ArrayList<>();

	protected ReplyTarget() {
	}

	public ReplyTarget(User receiver, User responser) {
		this.receiver = receiver;
		this.responser = responser;
	}

	public ReplyTarget(User receiver, User responser, Long participationId) {
		this.receiver = receiver;
		this.responser = responser;
		this.participationId = participationId;
	}

	public void setParticipationId(Long participationId) {
		this.participationId = participationId;
	}
}
