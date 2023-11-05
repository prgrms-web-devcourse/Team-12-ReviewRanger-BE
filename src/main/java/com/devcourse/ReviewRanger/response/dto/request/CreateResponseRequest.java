package com.devcourse.ReviewRanger.response.dto.request;

import java.util.List;

import com.devcourse.ReviewRanger.question.domain.QuestionType;

public record CreateResponseRequest(
	Long questionId,

	QuestionType questionType,

	List<String> answerText
) {
}
