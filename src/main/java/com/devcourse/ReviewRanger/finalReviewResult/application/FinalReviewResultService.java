package com.devcourse.ReviewRanger.finalReviewResult.application;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.*;
import static com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResult.Status.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResultAnswerDropdown;
import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResultAnswerHexStat;
import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResultAnswerObjects;
import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResultAnswerRating;
import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResultAnswerSubject;
import com.devcourse.ReviewRanger.finalReviewResult.domain.Hexstat;
import com.devcourse.ReviewRanger.finalReviewResult.dto.CheckFinalResultStatus;
import com.devcourse.ReviewRanger.finalReviewResult.dto.CreateFinalReplyRequest;
import com.devcourse.ReviewRanger.finalReviewResult.dto.CreateFinalReviewRequest;
import com.devcourse.ReviewRanger.finalReviewResult.dto.CreateFinalReviewResponse;
import com.devcourse.ReviewRanger.finalReviewResult.dto.FinalReviewResultListResponse;
import com.devcourse.ReviewRanger.finalReviewResult.dto.GetFinalReviewAnswerResponse;
import com.devcourse.ReviewRanger.finalReviewResult.dto.GetFinalReviewResultResponse;
import com.devcourse.ReviewRanger.finalReviewResult.repository.DropdownTypeRepository;
import com.devcourse.ReviewRanger.finalReviewResult.repository.FinalReviewResultRepository;
import com.devcourse.ReviewRanger.finalReviewResult.repository.HexstatTypeRepository;
import com.devcourse.ReviewRanger.finalReviewResult.repository.ObjectTypeRepository;
import com.devcourse.ReviewRanger.finalReviewResult.repository.RatingTypeRepository;
import com.devcourse.ReviewRanger.finalReviewResult.repository.SubjectTypeRepository;
import com.devcourse.ReviewRanger.participation.domain.Participation;
import com.devcourse.ReviewRanger.participation.repository.ParticipationRepository;
import com.devcourse.ReviewRanger.question.repository.QuestionRepository;
import com.devcourse.ReviewRanger.review.domain.Review;
import com.devcourse.ReviewRanger.review.repository.ReviewRepository;
import com.devcourse.ReviewRanger.user.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class FinalReviewResultService {

	private final FinalReviewResultRepository finalReviewResultRepository;
	private final SubjectTypeRepository subjectTypeRepository;
	private final ObjectTypeRepository objectTypeRepository;
	private final RatingTypeRepository ratingTypeRepository;
	private final HexstatTypeRepository hexstatTypeRepository;
	private final DropdownTypeRepository dropdownTypeRepository;
	private final UserRepository userRepository;
	private final ReviewRepository reviewRepository;
	private final QuestionRepository questionRepository;
	private final ParticipationRepository participationRepository;
	private final ReplyTargetRepository replyTargetRepository;

	public FinalReviewResultService(
		FinalReviewResultRepository finalReviewResultRepository,
		SubjectTypeRepository subjectTypeRepository,
		ObjectTypeRepository objectTypeRepository,
		RatingTypeRepository ratingTypeRepository,
		HexstatTypeRepository hexstatTypeRepository,
		DropdownTypeRepository dropdownTypeRepository,
		UserRepository userRepository,
		ReviewRepository reviewRepository,
		QuestionRepository questionRepository,
		ParticipationRepository participationRepository, ReplyTargetRepository replyTargetRepository) {
		this.finalReviewResultRepository = finalReviewResultRepository;
		this.objectTypeRepository = objectTypeRepository;
		this.ratingTypeRepository = ratingTypeRepository;
		this.hexstatTypeRepository = hexstatTypeRepository;
		this.dropdownTypeRepository = dropdownTypeRepository;
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

	public Slice<FinalReviewResultListResponse> getAllFinalReviewResultsOfCursorPaging(Long cursorId, Long userId,
		Pageable pageable) {
		validateUserId(userId);

		return finalReviewResultRepository.findAllFinalReviewResults(cursorId, userId, pageable);
	}

	@Transactional
	public CreateFinalReviewResponse createFinalReviewResult(CreateFinalReviewRequest createFinalReviewRequest) {
		Long userId = createFinalReviewRequest.userId();
		Long reviewId = createFinalReviewRequest.reviewId();
		validateUserId(userId);
		validateReviewId(reviewId);

		FinalReviewResult finalReviewResult = finalReviewResultRepository
			.findByReviewIdAndUserIdAndStatus(reviewId, userId, NOT_SENT)
			.orElseGet(createFinalReviewRequest::toEntity);
		List<Long> questionIdsOfFinalReview = finalReviewResult.questionIds();

		List<CreateFinalReplyRequest> replies = createFinalReviewRequest.replies();

		for (CreateFinalReplyRequest reply : replies) {
			Long questionId = reply.questionId();

			if (questionIdsOfFinalReview.contains(questionId)) {
				continue;
			}

			validateQuestionId(questionId);
			FinalQuestion finalQuestion = reply.toEntity();
			finalReviewResult.addQuestions(finalQuestion);
			finalReviewResultRepository.save(finalReviewResult);

			List<Object> answers = reply.answers();
			FinalQuestionType finalQuestionType = reply.questionType();

			for (Object answer : answers) {
				switch (finalQuestionType) {
					case SUBJECTIVE -> {
						FinalReviewResultAnswerSubject subjectAnswer = new FinalReviewResultAnswerSubject(userId,
							questionId);
						subjectAnswer.addAnswer(answer);
						subjectTypeRepository.save(subjectAnswer);
					}
					case SINGLE_CHOICE, MULTIPLE_CHOICE -> {
						FinalReviewResultAnswerObjects objectAnswer = new FinalReviewResultAnswerObjects(userId,
							questionId);
						objectAnswer.addAnswer(answer);
						objectTypeRepository.save(objectAnswer);
					}
					case RATING -> {
						FinalReviewResultAnswerRating ratingAnswer = new FinalReviewResultAnswerRating(userId,
							questionId);
						ratingAnswer.addAnswer(answer);
						ratingTypeRepository.save(ratingAnswer);
					}
					case DROPDOWN -> {
						FinalReviewResultAnswerDropdown dropdownAnswer = new FinalReviewResultAnswerDropdown(userId,
							questionId);
						dropdownAnswer.addAnswer(answer);
						dropdownTypeRepository.save(dropdownAnswer);
					}
					case HEXASTAT -> {
						FinalReviewResultAnswerHexStat hexStatAnswer = new FinalReviewResultAnswerHexStat(userId,
							questionId);
						hexStatAnswer.addAnswer(answer);
						hexstatTypeRepository.save(hexStatAnswer);
					}
				}
			}
		}

		return new CreateFinalReviewResponse(userId);
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

			FinalReviewResult finalReviewResult
				= finalReviewResultRepository.findByReviewIdAndUserIdAndStatus(reviewId, userId, NOT_SENT)
				.orElseThrow(() -> new RangerException(NOT_FOUND_FINAL_REVIEW_RESULT));

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
		List<FinalQuestion> questions = finalReviewResult.getQuestions();

		// 질문들의 식별자를 하나씩 사용하여 질문 타입에 맞는 답변들을 가져온다
		List<GetFinalReviewAnswerResponse> finalReviewAnswerResponseList = new ArrayList<>();

		for (FinalQuestion question : questions) {
			Long questionId = question.getQuestionId();
			FinalQuestionType finalQuestionType = question.getQuestionType();

			List<Long> answerIdList = new ArrayList<>();
			List<Object> answers = new ArrayList<>();

			switch (finalQuestionType) {
				case SUBJECTIVE -> {
					List<FinalReviewResultAnswerSubject> subjectAnswers
						= subjectTypeRepository.findAllByQuestionId(questionId); // TODO: 현재 조회하는 userID로 식별 필요

					for (FinalReviewResultAnswerSubject answer : subjectAnswers) {
						answerIdList.add(answer.getId());
						answers.add(answer.getSubjects());
					}
				}
				case SINGLE_CHOICE, MULTIPLE_CHOICE -> {
					List<FinalReviewResultAnswerObjects> objectAnswers
						= objectTypeRepository.findAllByQuestionId(questionId);

					for (FinalReviewResultAnswerObjects answer : objectAnswers) {
						answerIdList.add(answer.getId());
						answers.add(answer.getObject());
					}
				}
				case RATING -> {
					List<FinalReviewResultAnswerRating> ratingAnswers
						= ratingTypeRepository.findAllByQuestionId(questionId);

					for (FinalReviewResultAnswerRating answer : ratingAnswers) {
						answerIdList.add(answer.getId());
						answers.add(answer.getRate());
					}
				}
				case DROPDOWN -> {
					List<FinalReviewResultAnswerDropdown> dropdownAnswers
						= dropdownTypeRepository.findAllByQuestionId(questionId);

					for (FinalReviewResultAnswerDropdown answer : dropdownAnswers) {
						answerIdList.add(answer.getId());
						answers.add(answer.getDrops());
					}
				}
				case HEXASTAT -> {
					List<FinalReviewResultAnswerHexStat> hexstatAnswers
						= hexstatTypeRepository.findAllByQuestionId(questionId);

					for (FinalReviewResultAnswerHexStat answer : hexstatAnswers) {
						answerIdList.add(answer.getId());

						String statName = answer.getStatName();
						Double statScore = answer.getStatScore();
						Hexstat hexstat = new Hexstat(statName, statScore);
						answers.add(hexstat);
					}
				}
			}

			GetFinalReviewAnswerResponse getFinalReviewAnswer
				= new GetFinalReviewAnswerResponse(questionId, finalQuestionType, question.getQuestionTitle(),
				answerIdList, answers);

			finalReviewAnswerResponseList.add(getFinalReviewAnswer);
		}

		return finalReviewAnswerResponseList;
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

	private void validateUserId(Long userId) {
		userRepository.findById(userId)
			.orElseThrow(() -> new RangerException(NOT_FOUND_USER));
	}
}
