package com.devcourse.ReviewRanger.finalReviewResult.domain.agent;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.NOT_FOUND_FINAL_REVIEW_ANSWER_OF_SUBJECT;
import static com.devcourse.ReviewRanger.finalReviewResult.domain.FinalQuestionType.SUBJECTIVE;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.devcourse.ReviewRanger.common.exception.RangerException;
import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResultAnswer;
import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResultAnswerSubject;
import com.devcourse.ReviewRanger.finalReviewResult.repository.SubjectTypeRepository;

@Component
public class SubjectAgent extends RepositoryAdapter {
    private final SubjectTypeRepository subjectTypeRepository;

	public SubjectAgent(SubjectTypeRepository subjectTypeRepository) {
		super(SUBJECTIVE);
		this.subjectTypeRepository = subjectTypeRepository;
	}

	@Override
	public void save(FinalReviewResultAnswer finalReviewResult) {
		if (!(finalReviewResult instanceof FinalReviewResultAnswerSubject)) {
			throw new IllegalArgumentException("FinalReviewResultAnswer는 FinalReviewResultAnswerSubject의 객체가 아닙니다.");
		}

		subjectTypeRepository.save((FinalReviewResultAnswerSubject)finalReviewResult);
	}

	@Override
	public List<FinalReviewResultAnswer> findAnswers(Long questionId, Long userId) {
		FinalReviewResultAnswerSubject subject = subjectTypeRepository.findByQuestionIdAndUserId(questionId, userId)
			.orElseThrow(() -> new RangerException(NOT_FOUND_FINAL_REVIEW_ANSWER_OF_SUBJECT));

		List<FinalReviewResultAnswer> answers = new ArrayList<>();
		answers.add(subject);

		return answers;
	}

	@Override
	public FinalReviewResultAnswer findAnswer(Long id) {
		return subjectTypeRepository.findById(id)
			.orElseThrow(() -> new RangerException(NOT_FOUND_FINAL_REVIEW_ANSWER_OF_SUBJECT));
	}
}
