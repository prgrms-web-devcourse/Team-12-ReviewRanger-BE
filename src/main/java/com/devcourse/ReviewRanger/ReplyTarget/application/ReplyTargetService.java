package com.devcourse.ReviewRanger.ReplyTarget.application;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

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
import com.devcourse.ReviewRanger.reply.domain.Reply;
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
	public void createReviewTarget(Long participationId, Long reviewId,
		List<CreateReplyTargetRequest> createReplyTargetRequests) {
		for (CreateReplyTargetRequest createReplyTargetRequest : createReplyTargetRequests) {
			User receiver = userRepository.findById(createReplyTargetRequest.receiverId())
				.orElseThrow(() -> new RangerException(NOT_FOUND_USER));

			User responser = userRepository.findById(createReplyTargetRequest.responserId())
				.orElseThrow(() -> new RangerException(NOT_FOUND_USER));

			ReplyTarget replyTarget = createReplyTargetRequest.toEntity(receiver, responser);
			replyTarget.setParticipationId(participationId);
			replyTarget.setReviewId(reviewId);

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

	public List<ReplyTargetResponse> getAllRepliesByResponser(Long reviewId, Long responserId) {
		List<ReplyTarget> replyTargets = replyTargetRepository.findAllByReviewIdAndResponserId(reviewId, responserId);

		return getAllRepliesByTargets(replyTargets, this::getAllReplies);
	}

	public List<ReplyTargetResponse> getAllNonEmptyRepliesByResponser(Long reviewId, Long responserId) {
		List<ReplyTarget> replyTargets = replyTargetRepository.findAllByReviewIdAndResponserId(reviewId, responserId);

		return getAllRepliesByTargets(replyTargets, this::getAllNonEmptyReplies);
	}

	public List<ReplyTargetResponse> getAllNonEmptyRepliesByReceiver(Long reviewId, Long receiverId) {
		List<ReplyTarget> replyTargets = replyTargetRepository.findAllByReviewIdAndReceiverId(reviewId, receiverId);

		return getAllRepliesByTargets(replyTargets, this::getAllNonEmptyReplies);
	}

	private List<ReplyTargetResponse> getAllRepliesByTargets(List<ReplyTarget> replyTargets,
		Function<ReplyTarget, List<ReplyResponse>> replyResponseFunction) {

		List<ReplyTargetResponse> responses = new ArrayList<>();

		for (ReplyTarget replyTarget : replyTargets) {
			ReplyTargetResponse replyTargetResponse = ReplyTargetResponse.toResponse(replyTarget);
			List<ReplyResponse> replyResponses = replyResponseFunction.apply(replyTarget);
			replyTargetResponse.addRepliesResponse(replyResponses);
			responses.add(replyTargetResponse);
		}

		return responses;
	}

	private List<ReplyResponse> getAllReplies(ReplyTarget replyTarget) {
		return replyTarget.getReplies().stream()
			.map(this::mapToMultipleReply)
			.toList();
	}

	private List<ReplyResponse> getAllNonEmptyReplies(ReplyTarget replyTarget) {
		return replyTarget.getReplies().stream()
			.filter(Predicate.not(Reply::isEmptyAnswer))
			.map(this::mapToMultipleReply)
			.toList();
	}

	private ReplyResponse mapToMultipleReply(Reply reply) {
		if (reply.isNotOptionQuestionReply()) {
			return ReplyResponse.toResponse(reply);
		}

		QuestionOption questionOption = getQuestionOption(reply.getQuestionOptionId());
		GetQuestionOptionResponse questionOptionResponse = GetQuestionOptionResponse.toResponse(
			questionOption);

		return ReplyResponse.toResponse(reply, questionOptionResponse);
	}

	private QuestionOption getQuestionOption(Long questionOptionId) {
		return questionOptionRepository.findById(questionOptionId)
			.orElseThrow(() -> new RangerException(NOT_FOUND_QUESTION_OPTION));
	}
}
