package com.devcourse.ReviewRanger.reviewedTarget.application;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.common.exception.RangerException;
import com.devcourse.ReviewRanger.question.domain.QuestionOption;
import com.devcourse.ReviewRanger.question.dto.response.GetQuestionOptionResponse;
import com.devcourse.ReviewRanger.question.repository.QuestionOptionRepository;
import com.devcourse.ReviewRanger.reply.application.ReplyService;
import com.devcourse.ReviewRanger.reply.dto.response.ReplyResponse;
import com.devcourse.ReviewRanger.reviewedTarget.domain.ReviewedTarget;
import com.devcourse.ReviewRanger.reviewedTarget.dto.request.CreateReviewedTargetRequest;
import com.devcourse.ReviewRanger.reviewedTarget.dto.request.UpdateReviewedTargetRequest;
import com.devcourse.ReviewRanger.reviewedTarget.dto.response.ReviewedTargetResponse;
import com.devcourse.ReviewRanger.reviewedTarget.repository.ReviewedTargetRepository;
import com.devcourse.ReviewRanger.user.domain.User;
import com.devcourse.ReviewRanger.user.dto.UserResponse;
import com.devcourse.ReviewRanger.user.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class ReviewedTargetService {

	private final ReviewedTargetRepository reviewedTargetRepository;
	private final UserRepository userRepository;
	private final QuestionOptionRepository questionOptionRepository;
	private final ReplyService replyService;

	public ReviewedTargetService(ReviewedTargetRepository reviewedTargetRepository,
		UserRepository userRepository, QuestionOptionRepository questionOptionRepository, ReplyService replyService) {
		this.reviewedTargetRepository = reviewedTargetRepository;
		this.userRepository = userRepository;
		this.questionOptionRepository = questionOptionRepository;
		this.replyService = replyService;
	}

	@Transactional
	public void createReviewTarget(Long participationId,
		List<CreateReviewedTargetRequest> createReviewedTargetRequests) {
		for (CreateReviewedTargetRequest createReviewedTargetRequest : createReviewedTargetRequests) {
			ReviewedTarget reviewedTarget = createReviewedTargetRequest.toEntity();
			reviewedTarget.setParticipationId(participationId);

			ReviewedTarget savedReviewedTarget = reviewedTargetRepository.save(reviewedTarget);

			replyService.createReply(savedReviewedTarget, createReviewedTargetRequest.createReplyRequests());
		}
	}

	@Transactional
	public void updateReviewTarget(List<UpdateReviewedTargetRequest> updateReviewedTargetRequests) {
		for (UpdateReviewedTargetRequest updateReviewedTargetRequest : updateReviewedTargetRequests) {
			replyService.updateReply(updateReviewedTargetRequest.updateReplyRequests());
		}
	}

	public List<ReviewedTargetResponse> getAllRepliesByResponser(Long participationId) {
		List<ReviewedTarget> reviewedTargets = getAllByParticipationId(participationId);
		List<ReviewedTargetResponse> responses = new ArrayList<>();

		for (ReviewedTarget reviewedTarget : reviewedTargets) {
			User receiver = userRepository.findById(reviewedTarget.getReceiverId())
				.orElseThrow(() -> new RangerException(NOT_FOUND_USER));
			UserResponse receiverResponse = UserResponse.toResponse(receiver);

			ReviewedTargetResponse reviewedTargetResponse = ReviewedTargetResponse.toResponse(reviewedTarget,
				receiverResponse);

			List<ReplyResponse> replyResponses = reviewedTarget.getReplies().stream()
				.map(reply -> {
					User responser = userRepository.findById(reply.getResponserId())
						.orElseThrow(() -> new RangerException(NOT_FOUND_USER));
					UserResponse responserResponse = UserResponse.toResponse(responser);

					if (reply.getObjectOptionId() == null) {
						return ReplyResponse.toResponse(reply, responserResponse);
					}

					QuestionOption questionOption = questionOptionRepository.findById(reply.getObjectOptionId())
						.orElseThrow(() -> new RangerException(NOT_FOUND_QUESTION_OPTION));
					GetQuestionOptionResponse questionOptionResponse = GetQuestionOptionResponse.toResponse(
						questionOption);

					return ReplyResponse.toResponse(reply, responserResponse, questionOptionResponse);
				})
				.toList();

			reviewedTargetResponse.addRepliesResponse(replyResponses);
			responses.add(reviewedTargetResponse);
		}

		return responses;
	}

	public ReviewedTarget getByIdOrThrow(Long id) {
		return reviewedTargetRepository.findById(id)
			.orElseThrow(() -> new RangerException(NOT_FOUND_REVIEW_TARGET));
	}

	public List<ReviewedTarget> getAllByParticipationId(Long participationId) {
		return reviewedTargetRepository.findAllByParticipationId(participationId);
	}
}
