package com.devcourse.ReviewRanger.reviewedTarget.application;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.common.exception.RangerException;
import com.devcourse.ReviewRanger.reply.application.ReplyService;
import com.devcourse.ReviewRanger.reply.dto.response.ReplyResponse;
import com.devcourse.ReviewRanger.reviewedTarget.domain.ReviewedTarget;
import com.devcourse.ReviewRanger.reviewedTarget.dto.request.CreateReviewedTargetRequest;
import com.devcourse.ReviewRanger.reviewedTarget.dto.request.UpdateReviewedTargetRequest;
import com.devcourse.ReviewRanger.reviewedTarget.dto.response.RepliesByResponserResponse;
import com.devcourse.ReviewRanger.reviewedTarget.repository.ReviewedTargetRepository;
import com.devcourse.ReviewRanger.user.application.UserService;
import com.devcourse.ReviewRanger.user.domain.User;

@Service
@Transactional(readOnly = true)
public class ReviewedTargetService {

	private final ReviewedTargetRepository reviewedTargetRepository;
	private final ReplyService replyService;
	private final UserService userService;

	public ReviewedTargetService(ReviewedTargetRepository reviewedTargetRepository,
		ReplyService replyService, UserService userService) {
		this.reviewedTargetRepository = reviewedTargetRepository;
		this.replyService = replyService;
		this.userService = userService;
	}

	@Transactional
	public void createReviewTarget(Long participationId,
		List<CreateReviewedTargetRequest> createReviewedTargetRequests) {
		for (CreateReviewedTargetRequest createReviewedTargetRequest : createReviewedTargetRequests) {
			ReviewedTarget reviewedTarget = createReviewedTargetRequest.toEntity();
			reviewedTarget.setParticipationId(participationId);

			ReviewedTarget savedReviewedTargetId = reviewedTargetRepository.save(reviewedTarget);

			replyService.createReply(savedReviewedTargetId, createReviewedTargetRequest.responses());
		}
	}

	@Transactional
	public void updateReviewTarget(List<UpdateReviewedTargetRequest> updateReviewedTargetRequests) {
		for (UpdateReviewedTargetRequest updateReviewedTargetRequest : updateReviewedTargetRequests) {
			replyService.updateReply(updateReviewedTargetRequest.responses());
		}

	public List<RepliesByResponserResponse> getAllRepliesByResponser(Long participationId) {
		List<RepliesByResponserResponse> repliesByResponserResponses = new ArrayList<>();
		List<ReviewedTarget> reviewedTargets = getAllByParticipationId(participationId);

		for (ReviewedTarget reviewedTarget : reviewedTargets) {
			User user = userService.getUserOrThrow(reviewedTarget.getSubjectId());

			List<ReplyResponse> replyResponses = reviewedTarget.getReplies().stream()
				.map(ReplyResponse::new)
				.toList();

			RepliesByResponserResponse repliesByResponserResponse = new RepliesByResponserResponse(user.getId(),
				user.getName(), replyResponses);
			repliesByResponserResponses.add(repliesByResponserResponse);
		}

		return repliesByResponserResponses;
	}

	public ReviewedTarget getByIdOrThrow(Long id) {
		return reviewedTargetRepository.findById(id)
			.orElseThrow(() -> new RangerException(NOT_FOUND_REVIEW_TARGET));
	}

	public List<ReviewedTarget> getAllByParticipationId(Long participationId) {
		return reviewedTargetRepository.findAllByParticipationId(participationId);
	}
}
