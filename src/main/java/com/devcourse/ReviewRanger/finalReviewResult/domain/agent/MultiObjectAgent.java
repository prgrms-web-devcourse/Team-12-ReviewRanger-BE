package com.devcourse.ReviewRanger.finalReviewResult.domain.agent;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.NOT_FOUND_FINAL_REVIEW_ANSWER_OF_OBJECT;
import static com.devcourse.ReviewRanger.finalReviewResult.domain.FinalQuestionType.MULTIPLE_CHOICE;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.devcourse.ReviewRanger.common.exception.RangerException;
import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResultAnswer;
import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResultAnswerObjects;
import com.devcourse.ReviewRanger.finalReviewResult.repository.ObjectTypeRepository;

@Component
public class MultiObjectAgent extends RepositoryAdapter {

    private final ObjectTypeRepository objectTypeRepository;

	public MultiObjectAgent(ObjectTypeRepository objectTypeRepository) {
        super(MULTIPLE_CHOICE);
		this.objectTypeRepository = objectTypeRepository;
	}

	@Override
    public void save(FinalReviewResultAnswer finalReviewResult) {
        if (!(finalReviewResult instanceof FinalReviewResultAnswerObjects)) {
            throw new IllegalArgumentException("FinalReviewResultAnswer는 FinalReviewResultAnswerObjects의 객체가 아닙니다.");
        }

        objectTypeRepository.save((FinalReviewResultAnswerObjects) finalReviewResult);
    }

    @Override
    public List<FinalReviewResultAnswer> findAnswers(Long questionId, Long userId) {
        List<FinalReviewResultAnswerObjects> allByQuestionIdAndUserId = objectTypeRepository.findAllByQuestionIdAndUserId(questionId, userId);
        return new ArrayList<>(allByQuestionIdAndUserId);
    }

	@Override
	public FinalReviewResultAnswer findAnswer(Long id) {
		return objectTypeRepository.findById(id)
			.orElseThrow(() -> new RangerException(NOT_FOUND_FINAL_REVIEW_ANSWER_OF_OBJECT));
	}
}
