package com.devcourse.ReviewRanger.finalReviewResult.repository;

import org.springframework.data.domain.Slice;

import com.devcourse.ReviewRanger.finalReviewResult.dto.FinalReviewResultListResponse;

public interface FinalReviewResultCustomRepository {
	Slice<FinalReviewResultListResponse> findAllFinalReviewResults(Long cursorId, Long userId, Integer size);
}
