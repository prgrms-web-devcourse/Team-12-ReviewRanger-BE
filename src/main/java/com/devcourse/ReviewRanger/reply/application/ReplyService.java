package com.devcourse.ReviewRanger.reply.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public void createReply(Long responserId, ReviewedTarget reviewedTarget,
		List<CreateReplyRequest> createReplyRequests) {
		for (CreateReplyRequest request : createReplyRequests) {
			Long questionId = request.questionId();
			List<String> answerList = request.answerText();

			for (String answerText : answerList) {
				Reply reply = new Reply(responserId, reviewedTarget, questionId, answerText);
				replyRepository.save(reply);
			}
		}
	}
}
