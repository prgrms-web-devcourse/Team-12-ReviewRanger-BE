package com.devcourse.ReviewRanger.reply.dto.request;

import java.util.List;

import com.devcourse.ReviewRanger.question.domain.QuestionType;

public record CreateReplyRequest(
	Long questionId,

	QuestionType questionType,

	List<String> answerText
) {
}
