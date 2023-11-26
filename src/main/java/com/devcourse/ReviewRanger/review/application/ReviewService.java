package com.devcourse.ReviewRanger.review.application;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.*;
import static com.devcourse.ReviewRanger.participation.domain.ReviewStatus.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.common.exception.RangerException;
import com.devcourse.ReviewRanger.participation.application.ParticipationService;
import com.devcourse.ReviewRanger.participation.domain.ReviewStatus;
import com.devcourse.ReviewRanger.question.application.QuestionService;
import com.devcourse.ReviewRanger.question.dto.response.GetQuestionResponse;
import com.devcourse.ReviewRanger.review.domain.Review;
import com.devcourse.ReviewRanger.review.dto.request.CreateReviewRequest;
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

	public Slice<GetReviewResponse> getAllReviewsByRequesterOfCursorPaging(
		Long cursorId,
		Long requesterId,
		Pageable pageable
	) {
		Slice<Review> requesterReviews = reviewRepository.findByRequesterId(cursorId,requesterId, pageable);

		List<GetReviewResponse> reviewResponses = new ArrayList<>();
		for (Review requesterReview : requesterReviews) {
			Long reviewId = requesterReview.getId();
			Long responserCount = participationService.getResponserCount(reviewId);
			GetReviewResponse reviewResponse = new GetReviewResponse(requesterReview, responserCount);
			reviewResponses.add(reviewResponse);
		}

		return new SliceImpl<>(reviewResponses, requesterReviews.getPageable(), requesterReviews.hasNext());
	}

	public GetReviewDetailResponse getReviewDetailOrThrow(Long reviewId, Long responserId) {
		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(() -> new RangerException(NOT_FOUND_REVIEW));
		List<GetQuestionResponse> questionResponses = questionService.getAllQuestionsByReview(reviewId);

		List<GetUserResponse> receiverResponses = getAllReceivers(reviewId, responserId);

		return new GetReviewDetailResponse(review, questionResponses, receiverResponses);
	}

	public List<GetUserResponse> getAllReceivers(Long reviewId, Long responserId) {
		return participationService.getAllByReviewId(reviewId).stream()
			.filter(participation -> participation.getResponser().getId() != responserId)
			.map(participation -> userService.getUserOrThrow(participation.getResponser().getId()))
			.map(user -> new GetUserResponse(user.getId(), user.getName()))
			.toList();
	}

	@Transactional
	public void closeReviewOrThrow(Long reviewId) {
		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(() -> new RangerException(NOT_FOUND_REVIEW));
		review.changeStatus(ReviewStatus.DEADLINE);

		participationService.closeParticipationOrThrow(reviewId);
	}

	@Transactional
	public void deleteReviewOrThrow(Long id) {
		Review review = reviewRepository.findById(id)
			.orElseThrow(() -> new RangerException(NOT_FOUND_REVIEW));
		if(review.getStatus()!=PROCEEDING){
			throw new RangerException(NOT_REMOVE_AFTER_DEADLINE_REVIEW);
		}
		questionService.deleteQuestions(id);
		participationService.deleteParticipations(id);

		reviewRepository.delete(review);
	}
}
