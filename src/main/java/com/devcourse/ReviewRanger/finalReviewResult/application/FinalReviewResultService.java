package com.devcourse.ReviewRanger.finalReviewResult.application;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.common.exception.RangerException;
import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResult;
import com.devcourse.ReviewRanger.finalReviewResult.dto.CreateFinalReviewRequest;
import com.devcourse.ReviewRanger.finalReviewResult.dto.CreateFinalReviewResponse;
import com.devcourse.ReviewRanger.finalReviewResult.dto.FinalReviewResultListResponse;
import com.devcourse.ReviewRanger.finalReviewResult.repository.FinalReviewResultRepository;
import com.devcourse.ReviewRanger.review.application.ReviewService;
import com.devcourse.ReviewRanger.review.domain.Review;
import com.devcourse.ReviewRanger.review.repository.ReviewRepository;
import com.devcourse.ReviewRanger.user.application.UserService;
import com.devcourse.ReviewRanger.user.domain.User;
import com.devcourse.ReviewRanger.user.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class FinalReviewResultService {

	private final FinalReviewResultRepository finalReviewResultRepository;
	private final UserRepository userRepository;
	private final ReviewRepository reviewRepository;

	public FinalReviewResultService(FinalReviewResultRepository finalReviewResultRepository,
		UserRepository userRepository, ReviewRepository reviewRepository) {
		this.finalReviewResultRepository = finalReviewResultRepository;
		this.userRepository = userRepository;
		this.reviewRepository = reviewRepository;
	}

	public List<FinalReviewResultListResponse> getAllFinalReviewResults(Long userId) {
		return finalReviewResultRepository.findAllByUserId(userId)
			.stream()
			.map(FinalReviewResultListResponse::new).toList();
	}

	@Transactional
	public CreateFinalReviewResponse createFinalReviewResults(CreateFinalReviewRequest createFinalReviewRequest) {
		String userName = createFinalReviewRequest.receiverName();
		User user = userRepository.findByName(userName)
			.orElseThrow(() -> new RangerException(FAIL_USER_LOGIN));
		Long userId = user.getId();

		Long reviewId = createFinalReviewRequest.reviewId();
		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(() -> new RangerException(NOT_FOUND_REVIEW));
		String description = review.getDescription();

		// FinalReviewResult finalReviewResult = createFinalReviewRequest

		return null;
	}
}
