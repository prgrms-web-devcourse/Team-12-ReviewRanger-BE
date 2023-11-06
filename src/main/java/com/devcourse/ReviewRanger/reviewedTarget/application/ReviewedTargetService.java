package com.devcourse.ReviewRanger.reviewedTarget.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public void createReviewTarget(Long responserId, Long participationId,
		List<CreateReviewedTargetRequest> createReviewedTargetRequests) {
		for (CreateReviewedTargetRequest createReviewedTargetRequest : createReviewedTargetRequests) {
			Long subjectId = createReviewedTargetRequest.subjectId();

			ReviewedTarget savedReviewedTargetId = reviewedTargetRepository.save(
				new ReviewedTarget(subjectId, participationId));

			replyService.createReply(responserId, savedReviewedTargetId, createReviewedTargetRequest.responses());
		}
	}
}
