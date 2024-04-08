package com.devcourse.ReviewRanger.finalReviewResult.application;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.NOT_FOUND_FINAL_REVIEW_ANSWER_OF_SUBJECT;
import static com.devcourse.ReviewRanger.common.exception.ErrorCode.NOT_FOUND_FINAL_REVIEW_RESULT;
import static com.devcourse.ReviewRanger.common.exception.ErrorCode.NOT_FOUND_PARTICIPANTS;
import static com.devcourse.ReviewRanger.common.exception.ErrorCode.NOT_FOUND_QUESTION;
import static com.devcourse.ReviewRanger.common.exception.ErrorCode.NOT_FOUND_REVIEW;
import static com.devcourse.ReviewRanger.common.exception.ErrorCode.NOT_FOUND_USER;
import static com.devcourse.ReviewRanger.common.exception.ErrorCode.NOT_OWNER_OF_FINAL_REVIEW_RESULT;
import static com.devcourse.ReviewRanger.common.exception.ErrorCode.ONLY_DEADLINE_REVIEW_CAN_HAVE_FINAL_RESULT;
import static com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResult.Status.NOT_SENT;
import static com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResult.Status.SENT;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.ReplyTarget.domain.ReplyTarget;
import com.devcourse.ReviewRanger.ReplyTarget.repository.ReplyTargetRepository;
import com.devcourse.ReviewRanger.common.exception.RangerException;
import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalQuestion;
import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalQuestionType;
import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResult;
import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResultAnswer;
import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResultAnswerSubject;
import com.devcourse.ReviewRanger.finalReviewResult.domain.agent.RepositoryAdapter;
import com.devcourse.ReviewRanger.finalReviewResult.domain.answerType.Answer;
import com.devcourse.ReviewRanger.finalReviewResult.dto.CheckFinalResultStatus;
import com.devcourse.ReviewRanger.finalReviewResult.dto.CreateFinalReplyRequest;
import com.devcourse.ReviewRanger.finalReviewResult.dto.CreateFinalReviewRequest;
import com.devcourse.ReviewRanger.finalReviewResult.dto.CreateFinalReviewResponse;
import com.devcourse.ReviewRanger.finalReviewResult.dto.FinalReviewResultListResponse;
import com.devcourse.ReviewRanger.finalReviewResult.dto.GetFinalReviewAnswerResponse;
import com.devcourse.ReviewRanger.finalReviewResult.dto.GetFinalReviewResultResponse;
import com.devcourse.ReviewRanger.finalReviewResult.dto.UpdateAnswerOfSubject;
import com.devcourse.ReviewRanger.finalReviewResult.dto.answerType.FinalAnswerDto;
import com.devcourse.ReviewRanger.finalReviewResult.dto.paging.SliceResponse;
import com.devcourse.ReviewRanger.finalReviewResult.repository.FinalReviewResultRepository;
import com.devcourse.ReviewRanger.finalReviewResult.repository.SubjectTypeRepository;
import com.devcourse.ReviewRanger.participation.domain.Participation;
import com.devcourse.ReviewRanger.participation.domain.ReviewStatus;
import com.devcourse.ReviewRanger.participation.repository.ParticipationRepository;
import com.devcourse.ReviewRanger.question.repository.QuestionRepository;
import com.devcourse.ReviewRanger.review.domain.Review;
import com.devcourse.ReviewRanger.review.repository.ReviewRepository;
import com.devcourse.ReviewRanger.user.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class FinalReviewResultService {
	private final RepositoryAdapterFactory repositoryAdapterFactory;
	private final FinalReviewResultRepository finalReviewResultRepository;
	private final SubjectTypeRepository subjectTypeRepository;
	private final UserRepository userRepository;
	private final ReviewRepository reviewRepository;
	private final QuestionRepository questionRepository;
	private final ParticipationRepository participationRepository;
	private final ReplyTargetRepository replyTargetRepository;

	public FinalReviewResultService(
		RepositoryAdapterFactory repositoryAdapterFactory, FinalReviewResultRepository finalReviewResultRepository,
		SubjectTypeRepository subjectTypeRepository,
		UserRepository userRepository,
		ReviewRepository reviewRepository,
		QuestionRepository questionRepository,
		ParticipationRepository participationRepository, ReplyTargetRepository replyTargetRepository) {
		this.repositoryAdapterFactory = repositoryAdapterFactory;
		this.finalReviewResultRepository = finalReviewResultRepository;
		this.subjectTypeRepository = subjectTypeRepository;
		this.userRepository = userRepository;
		this.reviewRepository = reviewRepository;
		this.questionRepository = questionRepository;
		this.participationRepository = participationRepository;
		this.replyTargetRepository = replyTargetRepository;
	}

	public List<FinalReviewResultListResponse> getAllFinalReviewResults(Long userId) {
		return finalReviewResultRepository.findAllByUserIdAndStatus(userId, SENT)
			.stream()
			.map(FinalReviewResultListResponse::new).toList();
	}

	public SliceResponse<FinalReviewResultListResponse> getAllFinalReviewResultsOfCursorPaging(Long cursorId,
		Long userId, Integer size) {
		validateUserId(userId);
		Pageable pageable = PageRequest.of(0, size);
		Slice<FinalReviewResultListResponse> slice
			= finalReviewResultRepository.findAllFinalReviewResults(cursorId, userId, pageable);

		return new SliceResponse<>(
			slice.getContent(),
			slice.getNumber(),
			slice.getSize(),
			slice.hasNext()
		);
	}

	@Transactional
	public CreateFinalReviewResponse saveFinalReviewResult(CreateFinalReviewRequest createFinalReviewRequest) {
		Long userId = createFinalReviewRequest.userId();
		Long reviewId = createFinalReviewRequest.reviewId();
		validateReviewIdAndDeadLineStatus(reviewId);

		FinalReviewResult finalReviewResult = findUnsentFinalReviewResult(reviewId, userId);

		List<Long> questionIdsOfFinalReview = finalReviewResult.questionIds();
		List<CreateFinalReplyRequest> replies = createFinalReviewRequest.replies();

		for (CreateFinalReplyRequest reply : replies) {
			Long questionId = reply.questionId();

			if (questionIdsOfFinalReview.contains(questionId)) {
				continue;
			}

			validateQuestionId(questionId);
			mappingQuestionToReviewResult(reply, finalReviewResult);

			List<Answer> answers = reply.answers().stream().map(FinalAnswerDto::toEntity).toList();
			FinalQuestionType type = reply.questionType();

			FinalReviewResultAnswer finalAnswer = type.createAnswer();
			for (Answer answer : answers) {
				finalAnswer.addAnswer(answer);
			}

			repositoryAdapterFactory.from(type).save(finalAnswer);
		}

		return new CreateFinalReviewResponse(userId);
	}

	@Transactional
	public void updateAnswerOfSubject(UpdateAnswerOfSubject updateAnswerOfSubject) {
		validateReviewIdAndDeadLineStatus(updateAnswerOfSubject.reviewId());

		Long questionId = updateAnswerOfSubject.questionId();
		Long userId = updateAnswerOfSubject.userId();

		FinalReviewResultAnswerSubject subjectAnswer =
			subjectTypeRepository.findByQuestionIdAndUserId(questionId, userId)
				.orElseThrow(() -> new RangerException(NOT_FOUND_FINAL_REVIEW_ANSWER_OF_SUBJECT));

		String updateSubject = updateAnswerOfSubject.answer();
		subjectAnswer.update(updateSubject);
	}

	public CheckFinalResultStatus checkFinalResultStatus(Long reviewId) {
		validateReviewId(reviewId);

		Set<Long> haveUnsentReviewUserIds = new HashSet<>();

		List<FinalReviewResult> finalReviewResults
			= finalReviewResultRepository.findAllByReviewIdAndStatus(reviewId, NOT_SENT);

		for (FinalReviewResult finalReviewResult : finalReviewResults) {
			haveUnsentReviewUserIds.add(finalReviewResult.getUserId());
		}

		int participantNums = participationRepository.findAllByReviewId(reviewId).size();
		int notSentFinalResultNums = finalReviewResults.size();

		boolean checkStatus = (participantNums == notSentFinalResultNums);
		return new CheckFinalResultStatus(checkStatus, haveUnsentReviewUserIds);
	}

	@Transactional
	public void sendFinalResultToUsers(Long reviewId) {
		Review review = getReviewOrThrow(reviewId);
		review.toClose();

		List<Participation> participations = participationRepository.findAllByReviewId(reviewId);

		if (participations.isEmpty()) {
			throw new RangerException(NOT_FOUND_PARTICIPANTS);
		}

		Set<Long> userIds = new HashSet<>();

		for (Participation participation : participations) {
			participation.closeReview();
			Long participationId = participation.getId();
			List<ReplyTarget> replyTargets = replyTargetRepository.findAllByParticipationId(participationId);

			for (ReplyTarget replyTarget : replyTargets) {
				userIds.add(replyTarget.getReceiver().getId());
			}
		}

		for (Long userId : userIds) {
			validateUserId(userId);

			FinalReviewResult finalReviewResult = findUnsentFinalReviewResult(reviewId, userId);

			finalReviewResult.toSentStatus();
		}
	}

	public GetFinalReviewResultResponse getFinalReviewResultInfo(Long userId, Long finalReviewId) {
		FinalReviewResult finalReviewResult = getFinalReviewResultOrThrow(finalReviewId);

		boolean validateOwnerOfReviewResult = (finalReviewResult.getUserId() == userId);

		if (validateOwnerOfReviewResult) {
			return new GetFinalReviewResultResponse(finalReviewResult);
		} else {
			throw new RangerException(NOT_OWNER_OF_FINAL_REVIEW_RESULT);
		}

	}

	public List<GetFinalReviewAnswerResponse> getFinalReviewAnswerList(Long finalReviewId) {
		FinalReviewResult finalReviewResult = getFinalReviewResultOrThrow(finalReviewId);
		Long userId = finalReviewResult.getUserId();
		List<FinalQuestion> questions = finalReviewResult.getQuestions();

		// 질문들의 식별자를 하나씩 사용하여 질문 타입에 맞는 답변들을 가져온다
		List<GetFinalReviewAnswerResponse> finalReviewAnswerResponseList = new ArrayList<>();

		for (FinalQuestion question : questions) {
			Long questionId = question.getQuestionId();
			FinalQuestionType type = question.getQuestionType();

			List<Long> answerIdList = new ArrayList<>();
			List<Answer> answers = new ArrayList<>();

			RepositoryAdapter repositoryAdapter = repositoryAdapterFactory.from(type);
			List<FinalReviewResultAnswer> savedAnswers = repositoryAdapter.findAnswers(questionId, userId);

			for (FinalReviewResultAnswer saveAnswer : savedAnswers) {
				Long id = saveAnswer.getId();
				answerIdList.add(id);
				answers.add(repositoryAdapter.findAnswer(id));
			}

			GetFinalReviewAnswerResponse getFinalReviewAnswer
				= new GetFinalReviewAnswerResponse(questionId, type, question.getQuestionTitle(),
				answerIdList, answers);

			finalReviewAnswerResponseList.add(getFinalReviewAnswer);
		}

		return finalReviewAnswerResponseList;
	}

	private FinalReviewResult findUnsentFinalReviewResult(Long reviewId, Long userId) {
		return finalReviewResultRepository
			.findByReviewIdAndUserIdAndStatus(reviewId, userId, NOT_SENT)
			.orElseThrow(() -> new RangerException(NOT_FOUND_FINAL_REVIEW_RESULT));
	}

	private void mappingQuestionToReviewResult(CreateFinalReplyRequest reply, FinalReviewResult finalReviewResult) {
		FinalQuestion finalQuestion = reply.toEntity();
		finalReviewResult.addQuestions(finalQuestion);
		finalReviewResultRepository.save(finalReviewResult);
	}

	private FinalReviewResult getFinalReviewResultOrThrow(Long finalResultId) {
		return finalReviewResultRepository.findById(finalResultId)
			.orElseThrow(() -> new RangerException(NOT_FOUND_FINAL_REVIEW_RESULT));
	}

	private Review getReviewOrThrow(Long reviewId) {
		return reviewRepository.findById(reviewId)
			.orElseThrow(() -> new RangerException(NOT_FOUND_REVIEW));
	}

	private void validateQuestionId(Long questionId) {
		questionRepository.findById(questionId)
			.orElseThrow(() -> new RangerException(NOT_FOUND_QUESTION));
	}

	private void validateReviewId(Long reviewId) {
		reviewRepository.findById(reviewId)
			.orElseThrow(() -> new RangerException(NOT_FOUND_REVIEW));
	}

	private void validateReviewIdAndDeadLineStatus(Long reviewId) {
		reviewRepository.findByIdAndStatus(reviewId, ReviewStatus.DEADLINE)
			.orElseThrow(() -> new RangerException(ONLY_DEADLINE_REVIEW_CAN_HAVE_FINAL_RESULT));
	}

	private void validateUserId(Long userId) {
		userRepository.findById(userId)
			.orElseThrow(() -> new RangerException(NOT_FOUND_USER));
	}
}
