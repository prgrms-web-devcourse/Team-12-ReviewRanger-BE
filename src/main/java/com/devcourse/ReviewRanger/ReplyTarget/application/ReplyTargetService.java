package com.devcourse.ReviewRanger.ReplyTarget.application;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.ReplyTarget.domain.ReplyTarget;
import com.devcourse.ReviewRanger.ReplyTarget.dto.request.CreateReplyTargetRequest;
import com.devcourse.ReviewRanger.ReplyTarget.dto.request.UpdateReplyTargetRequest;
import com.devcourse.ReviewRanger.ReplyTarget.dto.response.ReplyTargetResponse;
import com.devcourse.ReviewRanger.ReplyTarget.repository.ReplyTargetRepository;
import com.devcourse.ReviewRanger.common.exception.RangerException;
import com.devcourse.ReviewRanger.question.domain.QuestionOption;
import com.devcourse.ReviewRanger.question.dto.response.GetQuestionOptionResponse;
import com.devcourse.ReviewRanger.question.repository.QuestionOptionRepository;
import com.devcourse.ReviewRanger.reply.application.ReplyService;
import com.devcourse.ReviewRanger.reply.dto.response.ReplyResponse;
import com.devcourse.ReviewRanger.user.domain.User;
import com.devcourse.ReviewRanger.user.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class ReplyTargetService {

	private final ReplyTargetRepository replyTargetRepository;
	private final UserRepository userRepository;
	private final QuestionOptionRepository questionOptionRepository;
	private final ReplyService replyService;

	public ReplyTargetService(ReplyTargetRepository replyTargetRepository,
		UserRepository userRepository, QuestionOptionRepository questionOptionRepository, ReplyService replyService) {
		this.replyTargetRepository = replyTargetRepository;
		this.userRepository = userRepository;
		this.questionOptionRepository = questionOptionRepository;
		this.replyService = replyService;
	}

	@Transactional
	public void createReviewTarget(Long participationId,
		List<CreateReplyTargetRequest> createReplyTargetRequests) {
		for (CreateReplyTargetRequest createReplyTargetRequest : createReplyTargetRequests) {
			User receiver = userRepository.findById(createReplyTargetRequest.receiverId())
				.orElseThrow(() -> new RangerException(NOT_FOUND_USER));

			User responer = userRepository.findById(createReplyTargetRequest.responserId())
				.orElseThrow(() -> new RangerException(NOT_FOUND_USER));

			ReplyTarget replyTarget = createReplyTargetRequest.toEntity(receiver, responer);
			replyTarget.setParticipationId(participationId);

			ReplyTarget savedReplyTarget = replyTargetRepository.save(replyTarget);

			replyService.createReply(savedReplyTarget, createReplyTargetRequest.createReplyRequests());
		}
	}

	@Transactional
	public void updateReviewTarget(List<UpdateReplyTargetRequest> updateReplyTargetRequests) {
		for (UpdateReplyTargetRequest updateReplyTargetRequest : updateReplyTargetRequests) {
			replyService.updateReply(updateReplyTargetRequest.updateReplyRequests());
		}
	}

	public List<ReplyTargetResponse> getAllRepliesByResponser(Long responserId) {
		List<ReplyTarget> replyTargets = replyTargetRepository.findAllByResponserId(responserId);
		List<ReplyTargetResponse> responses = getAllRepliesByReviewedTargets(replyTargets);

		return responses;
	}

	public List<ReplyTargetResponse> getAllRepliesByReceiver(Long receiverId) {
		List<ReplyTarget> replyTargets = replyTargetRepository.findAllByReceiverId(receiverId);
		List<ReplyTargetResponse> responses = getAllRepliesByReviewedTargets(replyTargets);

		return responses;
	}

	private List<ReplyTargetResponse> getAllRepliesByReviewedTargets(List<ReplyTarget> replyTargets) {
		List<ReplyTargetResponse> responses = new ArrayList<>();

		for (ReplyTarget replyTarget : replyTargets) {
			ReplyTargetResponse replyTargetResponse = ReplyTargetResponse.toResponse(replyTarget);

			List<ReplyResponse> replyResponses = getAllReplies(replyTarget);

			replyTargetResponse.addRepliesResponse(replyResponses);
			responses.add(replyTargetResponse);
		}

		return responses;
	}

	private List<ReplyResponse> getAllReplies(ReplyTarget replyTarget) {
		return replyTarget.getReplies().stream()
			.map(reply -> {
				if (reply.getQuestionOptionId() == null) {
					return ReplyResponse.toResponse(reply);
				}

				QuestionOption questionOption = questionOptionRepository.findById(reply.getQuestionOptionId())
					.orElseThrow(() -> new RangerException(NOT_FOUND_QUESTION_OPTION));
				GetQuestionOptionResponse questionOptionResponse = GetQuestionOptionResponse.toResponse(
					questionOption);

				return ReplyResponse.toResponse(reply, questionOptionResponse);
			})
			.toList();
	}

	public ReplyTarget getByIdOrThrow(Long id) {
		return replyTargetRepository.findById(id)
			.orElseThrow(() -> new RangerException(NOT_FOUND_REVIEW_TARGET));
	}

	public List<ReplyTarget> getAllByParticipationId(Long participationId) {
		return replyTargetRepository.findAllByParticipationId(participationId);
	}

}
