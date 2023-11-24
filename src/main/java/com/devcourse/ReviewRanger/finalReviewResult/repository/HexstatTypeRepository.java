package com.devcourse.ReviewRanger.finalReviewResult.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResultAnswerHexStat;

public interface HexstatTypeRepository extends JpaRepository<FinalReviewResultAnswerHexStat, Long> {
	List<FinalReviewResultAnswerHexStat> findAllByQuestionIdAndUserId(Long questionId, Long userId);
}
