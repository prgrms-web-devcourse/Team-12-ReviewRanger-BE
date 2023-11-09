package com.devcourse.ReviewRanger.finalReviewResult.dto;

import java.util.List;

import com.devcourse.ReviewRanger.question.domain.QuestionType;

public record CreateFinalReplyRequest(
	String questionTitle,
	QuestionType questionType,
	List<Object> answers
	// List<HexStat> hexStats
) {
}
