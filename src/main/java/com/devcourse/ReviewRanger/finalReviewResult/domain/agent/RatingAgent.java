package com.devcourse.ReviewRanger.finalReviewResult.domain.agent;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.NOT_FOUND_FINAL_REVIEW_ANSWER_OF_RATING;
import static com.devcourse.ReviewRanger.finalReviewResult.domain.FinalQuestionType.RATING;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.devcourse.ReviewRanger.common.exception.RangerException;
import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResultAnswer;
import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResultAnswerRating;
import com.devcourse.ReviewRanger.finalReviewResult.repository.RatingTypeRepository;

@Component
public class RatingAgent extends RepositoryAdapter {

	private final RatingTypeRepository ratingTypeRepository;

	public RatingAgent(RatingTypeRepository ratingTypeRepository) {
		super(RATING);
		this.ratingTypeRepository = ratingTypeRepository;
	}

	@Override
	public void save(FinalReviewResultAnswer finalReviewResult) {
		if (!(finalReviewResult instanceof FinalReviewResultAnswerRating)) {
			throw new IllegalArgumentException("FinalReviewResultAnswer는 FinalReviewResultAnswerRating의 객체가 아닙니다.");
		}

		ratingTypeRepository.save((FinalReviewResultAnswerRating)finalReviewResult);
	}

	@Override
	public List<FinalReviewResultAnswer> findAnswers(Long questionId, Long userId) {
		List<FinalReviewResultAnswerRating> allByQuestionIdAndUserId = ratingTypeRepository.findAllByQuestionIdAndUserId(
			questionId, userId);
		return new ArrayList<>(allByQuestionIdAndUserId);
	}

	@Override
	public FinalReviewResultAnswer findAnswer(Long id) {
		return ratingTypeRepository.findById(id)
			.orElseThrow(() -> new RangerException(NOT_FOUND_FINAL_REVIEW_ANSWER_OF_RATING));
	}
}
