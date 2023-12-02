package com.devcourse.ReviewRanger.finalReviewResult;

import static com.devcourse.ReviewRanger.finalReviewResult.domain.FinalQuestionType.*;

import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalQuestion;
import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalQuestionType;

public enum FinalQuestionFixture {

	ONCE_QUESTION(1L, SUBJECTIVE, "주관식 첫번째 질문입니다."),
	TWICE_QUESTION(2L, SINGLE_CHOICE, "객관식 두번째 질문입니다."),
	THREE_QUESTION(3L, MULTIPLE_CHOICE, "객관식 중복 세번째 질문입니다."),
	FOUR_QUESTION(4L, RATING, "별점 네번째 질문입니다."),
	FIVE_QUESTION(5L, DROPDOWN, "드롭다운 다섯번째 질문입니다."),
	SIX_QUESTION(6L, HEXASTAT, "육각스텟 여섯번째 질문입니다."),
	;

	private final Long questionId;

	private final FinalQuestionType questionType;

	private final String questionTitle;

	FinalQuestionFixture(Long questionId, FinalQuestionType questionType, String questionTitle) {
		this.questionId = questionId;
		this.questionType = questionType;
		this.questionTitle = questionTitle;
	}

	public FinalQuestion toEntity() {
		return new FinalQuestion(questionId, questionType, questionTitle);
	}
}
