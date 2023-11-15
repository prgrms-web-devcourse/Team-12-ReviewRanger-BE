package com.devcourse.ReviewRanger.finalReviewResult.dto;

import java.util.List;

import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalQuestionType;

public record GetFinalReviewAnswerResponse(
	Long questionId,
	FinalQuestionType finalQuestionType,
	String questionTitle,
	List<Long> answerIdList,
	List<Object> answers
) {
}
