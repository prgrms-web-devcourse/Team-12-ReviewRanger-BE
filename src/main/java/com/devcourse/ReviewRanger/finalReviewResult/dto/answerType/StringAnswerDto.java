package com.devcourse.ReviewRanger.finalReviewResult.dto.answerType;

import com.devcourse.ReviewRanger.finalReviewResult.domain.answerType.Answer;
import com.devcourse.ReviewRanger.finalReviewResult.domain.answerType.StringTypeAnswer;

public class StringAnswerDto implements FinalAnswerDto {
    String answer;

    public StringAnswerDto(String answer) {
        this.answer = answer;
    }

    @Override
    public Answer toEntity() {
        return new StringTypeAnswer(answer);
    }
}
