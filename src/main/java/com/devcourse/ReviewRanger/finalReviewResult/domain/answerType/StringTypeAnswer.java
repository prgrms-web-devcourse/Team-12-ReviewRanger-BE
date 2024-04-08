package com.devcourse.ReviewRanger.finalReviewResult.domain.answerType;

import lombok.Getter;

@Getter
public class StringTypeAnswer implements Answer {
    String data;

    public StringTypeAnswer(String data) {
        this.data = data;
    }
}
