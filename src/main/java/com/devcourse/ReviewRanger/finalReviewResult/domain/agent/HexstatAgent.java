package com.devcourse.ReviewRanger.finalReviewResult.domain.agent;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.NOT_FOUND_FINAL_REVIEW_ANSWER_OF_HEXSTAT;
import static com.devcourse.ReviewRanger.finalReviewResult.domain.FinalQuestionType.HEXASTAT;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.devcourse.ReviewRanger.common.exception.RangerException;
import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResultAnswer;
import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResultAnswerHexStat;
import com.devcourse.ReviewRanger.finalReviewResult.repository.HexstatTypeRepository;

@Component
public class HexstatAgent extends RepositoryAdapter {

	private final HexstatTypeRepository hexstatTypeRepository;

	public HexstatAgent(HexstatTypeRepository hexstatTypeRepository) {
		super(HEXASTAT);
		this.hexstatTypeRepository = hexstatTypeRepository;
	}

	@Override
	public void save(FinalReviewResultAnswer finalReviewResult) {
		if (!(finalReviewResult instanceof FinalReviewResultAnswerHexStat)) {
			throw new IllegalArgumentException("FinalReviewResultAnswer는 FinalReviewResultAnswerHexStat의 객체가 아닙니다.");
		}

		hexstatTypeRepository.save((FinalReviewResultAnswerHexStat)finalReviewResult);
	}

	@Override
	public List<FinalReviewResultAnswer> findAnswers(Long questionId, Long userId) {
		List<FinalReviewResultAnswerHexStat> allByQuestionIdAndUserId = hexstatTypeRepository.findAllByQuestionIdAndUserId(
			questionId, userId);
		return new ArrayList<>(allByQuestionIdAndUserId);
	}

	@Override
	public FinalReviewResultAnswer findAnswer(Long id) {
		return hexstatTypeRepository.findById(id)
			.orElseThrow(() -> new RangerException(NOT_FOUND_FINAL_REVIEW_ANSWER_OF_HEXSTAT));
	}
}
