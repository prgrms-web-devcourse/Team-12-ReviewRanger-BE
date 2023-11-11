package com.devcourse.ReviewRanger.finalReviewResult.dto;

import java.util.List;

import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalQuestionType;

public record CreateFinalReplyRequest(
	String questionTitle,
	FinalQuestionType questionType,
	List<Object> answers
) {
}
