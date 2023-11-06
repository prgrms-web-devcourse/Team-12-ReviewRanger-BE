package com.devcourse.ReviewRanger.reply.application;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.common.exception.RangerException;
import com.devcourse.ReviewRanger.question.domain.QuestionType;
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
			QuestionType questionType = request.questionType();
			List<String> answerList = request.answerText();

			switch (questionType) {
				case SUBJECTIVE -> saveSubjective(responserId, reviewedTarget, questionId, answerList.get(0));
				case OBJECTIVE_UNIQUE ->
					saveUniqueObjective(responserId, reviewedTarget, questionId, answerList.get(0));
				case OBJECTIVE_DUPLICATE -> saveDuplicateObjective(responserId, reviewedTarget, questionId, answerList);
			}

			for (String answerText : answerList) {
				Reply reply = new Reply(responserId, reviewedTarget, questionId, answerText);
				replyRepository.save(reply);
			}
		}
	}

	public Reply findById(Long id) {
		return replyRepository.findById(id)
			.orElseThrow(() -> new RangerException(NOT_FOUND_REPLY));
	}

	private void saveSubjective(Long responserId, ReviewedTarget reviewedTargetId, Long questionId, String answer) {
		Reply response = new Reply(responserId, reviewedTargetId, questionId, answer);
		replyRepository.save(response);
	}

	private void saveUniqueObjective(Long responserId, ReviewedTarget savedEachSurveyResultId, Long questionId,
		String answer) {
		Reply response = new Reply(responserId, savedEachSurveyResultId, questionId, Long.parseLong(answer));
		replyRepository.save(response);
	}

	private void saveDuplicateObjective(Long responserId, ReviewedTarget savedEachSurveyResultId, Long questionId,
		List<String> answers) {
		for (String answer : answers) {
			Reply response = new Reply(responserId, savedEachSurveyResultId, questionId, Long.parseLong(answer));
			replyRepository.save(response);
		}
	}
}
