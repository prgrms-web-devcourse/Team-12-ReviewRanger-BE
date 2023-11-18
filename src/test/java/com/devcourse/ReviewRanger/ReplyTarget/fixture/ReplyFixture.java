package com.devcourse.ReviewRanger.ReplyTarget.fixture;

import com.devcourse.ReviewRanger.reply.domain.Reply;

public enum ReplyFixture {

	SUBJECT_REPLY(1L, null, "주관식 답변", null, null),
	OBJECT_REPLY1(2L, 1L, null, null, null),
	OBJECT_REPLY2(2L, 2L, null, null, null);

	private final Long questionId;
	private final Long questionOptionId;
	private final String answerText;
	private final Double rating;
	private final Integer hexastat;

	ReplyFixture(Long questionId, Long questionOptionId, String answerText, Double rating, Integer hexastat) {
		this.questionId = questionId;
		this.questionOptionId = questionOptionId;
		this.answerText = answerText;
		this.rating = rating;
		this.hexastat = hexastat;
	}

	public Reply toEntity() {
		return new Reply(questionId, questionOptionId, answerText, rating, hexastat);
	}
}
