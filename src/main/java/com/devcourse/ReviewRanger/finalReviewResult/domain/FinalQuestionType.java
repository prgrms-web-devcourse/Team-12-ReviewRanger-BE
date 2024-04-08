package com.devcourse.ReviewRanger.finalReviewResult.domain;

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
}
