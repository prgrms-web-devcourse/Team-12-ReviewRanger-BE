package com.devcourse.ReviewRanger.finalReviewResult.domain;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.*;

import java.util.Arrays;

import com.devcourse.ReviewRanger.common.exception.RangerException;
import com.devcourse.ReviewRanger.question.domain.QuestionType;

public enum FinalQuestionType {
	SUBJECTIVE(FinalReviewResultAnswerSubject::new),
	SINGLE_CHOICE(FinalReviewResultAnswerObjects::new),
	MULTIPLE_CHOICE(FinalReviewResultAnswerObjects::new),
	RATING(FinalReviewResultAnswerRating::new),
	DROPDOWN(FinalReviewResultAnswerDropdown::new),
	HEXASTAT(FinalReviewResultAnswerHexStat::new);

	private final AnswerSupplier answerSupplier;

	FinalQuestionType(AnswerSupplier answerSupplier) {
		this.answerSupplier = answerSupplier;
	}

	public FinalReviewResultAnswer createAnswer() {
		return answerSupplier.createAnswer();
	}

	public static QuestionType convertToQuestionType(FinalQuestionType finalQuestionType) {
		return Arrays.stream(QuestionType.values())
			.filter(questionType -> questionType.name().equals(finalQuestionType.name()))
			.findFirst()
			.orElseThrow(() -> new RangerException(NOT_FOUND_QUESTION_TYPE));
	}
}
