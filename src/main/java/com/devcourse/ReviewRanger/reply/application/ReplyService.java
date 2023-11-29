package com.devcourse.ReviewRanger.reply.application;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.ReplyTarget.domain.ReplyTarget;
import com.devcourse.ReviewRanger.common.exception.RangerException;
import com.devcourse.ReviewRanger.reply.domain.Reply;
import com.devcourse.ReviewRanger.reply.dto.request.CreateReplyRequest;
import com.devcourse.ReviewRanger.reply.dto.request.UpdateReplyRequest;
import com.devcourse.ReviewRanger.reply.repository.ReplyRepository;

@Service
@Transactional(readOnly = true)
public class ReplyService {

	private final ReplyRepository replyRepository;

	public ReplyService(ReplyRepository replyRepository) {
		this.replyRepository = replyRepository;
	}

	@Transactional
	public void createReply(ReplyTarget replyTarget, List<CreateReplyRequest> createReplyRequests) {
		for (CreateReplyRequest createReplyRequest : createReplyRequests) {
			Reply reply = createReplyRequest.toEntity();

			if (createReplyRequest.isRequiredQuestion()) {
				reply.validateReplyInputsOrThrow();
			}
			reply.assignReviewedTarget(replyTarget);
			replyRepository.save(reply);
		}
	}

	@Transactional
	public void updateReply(ReplyTarget replyTarget, List<UpdateReplyRequest> updateReplyRequests) {
		for (UpdateReplyRequest updateReplyRequest : updateReplyRequests) {
			Reply reply = updateReplyRequest.toEntity();

			if (updateReplyRequest.isRequiredQuestion()) {
				reply.validateReplyInputsOrThrow();
			}
			reply.assignReviewedTarget(replyTarget);
			replyRepository.save(reply);
		}
	}

	public void deleteReplise(List<Reply> replies) {
		replyRepository.deleteAll(replies);
	}

	public Reply getByIdOrThrow(Long id) {
		return replyRepository.findById(id)
			.orElseThrow(() -> new RangerException(NOT_FOUND_REPLY));
	}
}
