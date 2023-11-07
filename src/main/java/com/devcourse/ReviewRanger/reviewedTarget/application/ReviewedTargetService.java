package com.devcourse.ReviewRanger.reviewedTarget.application;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.common.exception.RangerException;
import com.devcourse.ReviewRanger.reply.application.ReplyService;
import com.devcourse.ReviewRanger.reviewedTarget.domain.ReviewedTarget;
import com.devcourse.ReviewRanger.reviewedTarget.dto.request.CreateReviewedTargetRequest;
import com.devcourse.ReviewRanger.reviewedTarget.repository.ReviewedTargetRepository;

@Service
@Transactional(readOnly = true)
public class ReviewedTargetService {

	private final ReviewedTargetRepository reviewedTargetRepository;
	private final ReplyService replyService;

	public ReviewedTargetService(ReviewedTargetRepository reviewedTargetRepository,
		ReplyService replyService) {
		this.reviewedTargetRepository = reviewedTargetRepository;
		this.replyService = replyService;
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

	public ReviewedTarget getByIdOrThrow(Long id) {
		return reviewedTargetRepository.findById(id)
			.orElseThrow(() -> new RangerException(NOT_FOUND_REVIEW_TARGET));
	}

	public List<ReviewedTarget> getAllByParticipationId(Long participationId) {
		return reviewedTargetRepository.findAllByParticipationId(participationId);
	}
}
