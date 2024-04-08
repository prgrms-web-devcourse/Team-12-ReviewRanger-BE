package com.devcourse.ReviewRanger.finalReviewResult.dto.answerType;

import com.devcourse.ReviewRanger.finalReviewResult.domain.answerType.Answer;
import com.devcourse.ReviewRanger.finalReviewResult.domain.answerType.DoubleTypeAnswer;

public class DoubleAnswerDto implements FinalAnswerDto {
    Double answer;

    public DoubleAnswerDto(Double answer) {
        this.answer = answer;
    }

    @Override
    public Answer toEntity() {
        return new DoubleTypeAnswer(answer);
    }
}
