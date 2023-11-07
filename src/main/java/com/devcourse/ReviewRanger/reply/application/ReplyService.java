package com.devcourse.ReviewRanger.reply.application;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.common.exception.RangerException;
import com.devcourse.ReviewRanger.reply.domain.Reply;
import com.devcourse.ReviewRanger.reply.dto.request.CreateReplyRequest;
import com.devcourse.ReviewRanger.reply.repository.ReplyRepository;
import com.devcourse.ReviewRanger.reviewedTarget.domain.ReviewedTarget;

@Service
@Transactional(readOnly = true)
public class ReplyService {

	private final ReplyRepository replyRepository;

	public ReplyService(ReplyRepository replyRepository) {
		this.replyRepository = replyRepository;
	}

	@Transactional
	public void createReply(ReviewedTarget reviewedTarget, List<CreateReplyRequest> createReplyRequests) {
		for (CreateReplyRequest createReplyRequest : createReplyRequests) {
			Reply reply = createReplyRequest.toEntity();
			reply.assignReviewedTarget(reviewedTarget);
			replyRepository.save(reply);
		}
	}

	public Reply getByIdOrThrow(Long id) {
		return replyRepository.findById(id)
			.orElseThrow(() -> new RangerException(NOT_FOUND_REPLY));
	}

	public List<Reply> getAllByReviewedTargetId(Long reviewedTargetId) {
		return replyRepository.findAllByReviewedTargetId(reviewedTargetId);
	}
}
