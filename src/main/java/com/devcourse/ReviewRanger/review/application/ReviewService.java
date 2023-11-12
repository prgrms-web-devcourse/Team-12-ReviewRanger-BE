package com.devcourse.ReviewRanger.review.application;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.*;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.common.exception.RangerException;
import com.devcourse.ReviewRanger.participation.application.ParticipationService;
import com.devcourse.ReviewRanger.participation.domain.ReviewStatus;

import com.devcourse.ReviewRanger.question.application.QuestionService;
import com.devcourse.ReviewRanger.question.dto.response.GetQuestionResponse;
import com.devcourse.ReviewRanger.review.domain.Review;
import com.devcourse.ReviewRanger.review.dto.request.CreateReviewRequest;
import com.devcourse.ReviewRanger.review.dto.response.GetReviewDetailFirstResponse;
import com.devcourse.ReviewRanger.review.dto.response.GetReviewDetailResponse;
import com.devcourse.ReviewRanger.review.dto.response.GetReviewResponse;
import com.devcourse.ReviewRanger.review.repository.ReviewRepository;
import com.devcourse.ReviewRanger.user.application.UserService;
import com.devcourse.ReviewRanger.user.dto.GetUserResponse;

@Service
@Transactional(readOnly = true)
public class ReviewService {

	private final ReviewRepository reviewRepository;

	private final UserService userService;
	private final QuestionService questionService;
	private final ParticipationService participationService;

	public ReviewService(ReviewRepository reviewRepository, UserService userService, QuestionService questionService,
		ParticipationService participationService) {
		this.reviewRepository = reviewRepository;
		this.userService = userService;
		this.questionService = questionService;
		this.participationService = participationService;
	}

	@Transactional
	public boolean createReview(Long requesterId, CreateReviewRequest createReviewRequest) {
		Review review = createReviewRequest.toEntity();
		review.assignRequesterId(requesterId);
		Review savedReview = reviewRepository.save(review);

		questionService.createQuestions(savedReview.getId(), createReviewRequest.creatQuestionRequests());
		participationService.createParticipations(savedReview.getId(), createReviewRequest.responserIds());

		return true;
	}

	public List<GetReviewResponse> getAllReviewsByRequester(Long requesterId) {
		List<Review> requesterReviews = reviewRepository.findByRequesterId(requesterId);

		List<GetReviewResponse> reviewResponses = new ArrayList<>();
		for (Review requesterReview : requesterReviews) {
			Long reviewId = requesterReview.getId();
			Long responserCount = participationService.getResponserCount(reviewId);
			GetReviewResponse reviewResponse = new GetReviewResponse(requesterReview, responserCount);
			reviewResponses.add(reviewResponse);
		}

		return reviewResponses;
	}

	public GetReviewDetailResponse getReviewDetailOrThrow(Long reviewId) {
		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(() -> new RangerException(NOT_FOUND_REVIEW));
		List<GetQuestionResponse> questionResponses = questionService.getAllQuestionsByReview(reviewId);

		return new GetReviewDetailResponse(review, questionResponses);
	}

	public GetReviewDetailFirstResponse getReviewDetailFirstOrThrow(Long reviewId, Long responserId) {
		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(() -> new RangerException(NOT_FOUND_REVIEW));
		List<GetQuestionResponse> questionResponses = questionService.getAllQuestionsByReview(reviewId);

		List<GetUserResponse> receiverResponses = getAllReceivers(reviewId, responserId);

		return new GetReviewDetailFirstResponse(review, questionResponses, receiverResponses);
	}

	public List<GetUserResponse> getAllReceivers(Long reviewId, Long responserId) {
		return participationService.getAllByReviewId(reviewId).stream()
			.filter(participation -> participation.getResponserId() != responserId)
			.map(participation -> userService.getUserOrThrow(participation.getResponserId()))
			.map(user -> new GetUserResponse(user.getId(), user.getName()))
			.toList();
	}

	@Transactional
	public void closeReviewOrThrow(Long reviewId) {
		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(() -> new RangerException(NOT_FOUND_REVIEW));
		review.changeStatus(ReviewStatus.END);

		participationService.closeParticipationOrThrow(reviewId);
	}
}
