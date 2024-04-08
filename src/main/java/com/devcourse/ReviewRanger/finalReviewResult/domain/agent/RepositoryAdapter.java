package com.devcourse.ReviewRanger.finalReviewResult.domain.agent;

import java.util.List;

import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalQuestionType;
import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResultAnswer;

public abstract class RepositoryAdapter {
    public FinalQuestionType finalQuestionType;

    public RepositoryAdapter(FinalQuestionType finalQuestionType) {
        this.finalQuestionType = finalQuestionType;
    }

    public abstract void save(FinalReviewResultAnswer finalReviewResult);
    public abstract List<FinalReviewResultAnswer> findAnswers(Long questionId, Long userId);
    public abstract FinalReviewResultAnswer findAnswer(Long id);
}
