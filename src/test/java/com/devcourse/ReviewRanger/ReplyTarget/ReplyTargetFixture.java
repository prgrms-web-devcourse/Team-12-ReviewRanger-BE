package com.devcourse.ReviewRanger.ReplyTarget;

import static com.devcourse.ReviewRanger.ReplyTarget.fixture.ReplyFixture.*;
import static com.devcourse.ReviewRanger.user.UserFixture.*;

import java.util.List;

import com.devcourse.ReviewRanger.ReplyTarget.domain.ReplyTarget;
import com.devcourse.ReviewRanger.reply.domain.Reply;
import com.devcourse.ReviewRanger.user.domain.User;

public enum ReplyTargetFixture {

	BASIC_FIXTURE(
		SPENCER_FIXTURE.toEntity(),
		SUYEON_FIXTURE.toEntity(),
		1L,
		List.of(
			SUBJECT_REPLY.toEntity(),
			OBJECT_REPLY1.toEntity(),
			OBJECT_REPLY2.toEntity()
		)
	);

	private final User receiver;
	private final User responser;
	private final Long participationId;
	private final List<Reply> replies;

	ReplyTargetFixture(User receiver, User responser, Long participationId, List<Reply> replies) {
		this.receiver = receiver;
		this.responser = responser;
		this.participationId = participationId;
		this.replies = replies;
	}

	public ReplyTarget toEntity() {
		return new ReplyTarget(receiver, responser, participationId, replies);
	}
}
