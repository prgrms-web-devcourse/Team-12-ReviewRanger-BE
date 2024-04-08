package com.devcourse.ReviewRanger.finalReviewResult.domain.agent;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.NOT_FOUND_FINAL_REVIEW_ANSWER_OF_DROPDOWN;
import static com.devcourse.ReviewRanger.finalReviewResult.domain.FinalQuestionType.DROPDOWN;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.devcourse.ReviewRanger.common.exception.RangerException;
import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResultAnswer;
import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResultAnswerDropdown;
import com.devcourse.ReviewRanger.finalReviewResult.repository.DropdownTypeRepository;

@Component
public class DropdownAgent extends RepositoryAdapter {

	private final DropdownTypeRepository dropdownTypeRepository;

	public DropdownAgent(DropdownTypeRepository dropdownTypeRepository) {
		super(DROPDOWN);
		this.dropdownTypeRepository = dropdownTypeRepository;
	}

	@Override
	public void save(FinalReviewResultAnswer finalReviewResult) {
		if (!(finalReviewResult instanceof FinalReviewResultAnswerDropdown)) {
			throw new IllegalArgumentException("FinalReviewResultAnswer는 FinalReviewResultAnswerDropdown의 객체가 아닙니다.");
		}

		dropdownTypeRepository.save((FinalReviewResultAnswerDropdown)finalReviewResult);
	}

	@Override
	public List<FinalReviewResultAnswer> findAnswers(Long questionId, Long userId) {
		List<FinalReviewResultAnswerDropdown> allByQuestionIdAndUserId = dropdownTypeRepository.findAllByQuestionIdAndUserId(
			questionId, userId);
		return new ArrayList<>(allByQuestionIdAndUserId);
	}

	@Override
	public FinalReviewResultAnswer findAnswer(Long id) {
		return dropdownTypeRepository.findById(id)
			.orElseThrow(() -> new RangerException(NOT_FOUND_FINAL_REVIEW_ANSWER_OF_DROPDOWN));
	}
}
