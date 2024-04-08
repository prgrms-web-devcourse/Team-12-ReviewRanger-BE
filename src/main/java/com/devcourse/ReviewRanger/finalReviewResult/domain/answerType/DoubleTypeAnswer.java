package com.devcourse.ReviewRanger.finalReviewResult.domain.answerType;

import lombok.Getter;

@Getter
public class DoubleTypeAnswer implements Answer {
    Double data;

    public DoubleTypeAnswer(Double data) {
        this.data = data;
    }
}
