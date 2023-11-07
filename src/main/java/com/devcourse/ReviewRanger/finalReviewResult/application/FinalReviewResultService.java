package com.devcourse.ReviewRanger.finalReviewResult.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.finalReviewResult.dto.FinalReviewResultListResponse;
import com.devcourse.ReviewRanger.finalReviewResult.repository.FinalReviewResultRepository;

@Service
@Transactional(readOnly = true)
public class FinalReviewResultService {

	private final FinalReviewResultRepository finalReviewResultRepository;

	public FinalReviewResultService(FinalReviewResultRepository finalReviewResultRepository) {
		this.finalReviewResultRepository = finalReviewResultRepository;
	}

	public List<FinalReviewResultListResponse> getAllFinalReviewResults(Long userId) {
		return finalReviewResultRepository.findAllByUserId(userId)
			.stream()
			.map(FinalReviewResultListResponse::new).toList();
	}
}
