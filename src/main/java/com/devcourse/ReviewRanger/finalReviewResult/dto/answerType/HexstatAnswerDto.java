package com.devcourse.ReviewRanger.finalReviewResult.dto.answerType;

import com.devcourse.ReviewRanger.finalReviewResult.domain.Hexstat;
import com.devcourse.ReviewRanger.finalReviewResult.domain.answerType.Answer;

public class HexstatAnswerDto implements FinalAnswerDto {
    private String statName;
    private Double statScore;

    public HexstatAnswerDto(String statName, Double statScore) {
        this.statName = statName;
        this.statScore = statScore;
    }

    @Override
    public Answer toEntity() {
        return new Hexstat(statName, statScore);
    }
}
