package com.devcourse.ReviewRanger.finalReviewResult.application;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.*;
import static com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResult.Status.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.devcourse.ReviewRanger.finalReviewResult.dto.CreateFinalReplyRequest;
import com.devcourse.ReviewRanger.finalReviewResult.dto.CreateFinalReviewRequest;
import com.devcourse.ReviewRanger.finalReviewResult.dto.CreateFinalReviewResponse;
import com.devcourse.ReviewRanger.finalReviewResult.dto.FinalReviewResultListResponse;
import com.devcourse.ReviewRanger.finalReviewResult.repository.DropdownTypeRepository;
import com.devcourse.ReviewRanger.finalReviewResult.repository.FinalReviewResultRepository;
import com.devcourse.ReviewRanger.finalReviewResult.repository.HexstatTypeRepository;
import com.devcourse.ReviewRanger.finalReviewResult.repository.ObjectTypeRepository;
import com.devcourse.ReviewRanger.finalReviewResult.repository.RatingTypeRepository;
import com.devcourse.ReviewRanger.finalReviewResult.repository.SubjectTypeRepository;
import com.devcourse.ReviewRanger.participation.domain.Participation;
import com.devcourse.ReviewRanger.participation.repository.ParticipationRepository;
import com.devcourse.ReviewRanger.question.domain.Question;
import com.devcourse.ReviewRanger.question.domain.QuestionType;
import com.devcourse.ReviewRanger.question.repository.QuestionRepository;
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

	@Transactional
	public CreateFinalReviewResponse createFinalReviewResult(CreateFinalReviewRequest createFinalReviewRequest) {
		// 유효한 user인지 검사
		Long userId = createFinalReviewRequest.userId();
		userRepository.findById(userId)
			.orElseThrow(() -> new RangerException(NOT_FOUND_USER));

		validateReviewId(createFinalReviewRequest.reviewId());

		List<CreateFinalReplyRequest> replies = createFinalReviewRequest.replies();
		FinalReviewResult finalReviewResult = createFinalReviewRequest.toEntity();

		for (CreateFinalReplyRequest reply : replies) {
			FinalQuestionType finalQuestionType = reply.questionType();
			String question_title = reply.questionTitle();
			QuestionType questionType = FinalQuestionType.convertToQuestionType(finalQuestionType);

			// 내가 question id 찾아와야 함!!
			Question question = questionRepository.findByTitleAndType(question_title, questionType)
				.orElseThrow(() -> new RangerException(NOT_FOUND_QUESTION));

			Long question_id = question.getId();

			FinalQuestion finalQuestion = new FinalQuestion(question_id, finalQuestionType, question_title);
			finalReviewResult.addQuestions(finalQuestion);

			// answer를 가져와서 question type에 따라 해당하는 테이블에 데이터를 저장하자 => addAnswer 호출
			List<Object> answers = reply.answers();

			for (Object answer : answers) {
				switch (finalQuestionType) {
					case SUBJECTIVE -> {
						FinalReviewResultAnswerSubject subjectAnswer = new FinalReviewResultAnswerSubject(question_id);
						subjectAnswer.addAnswer(answer);
						subjectTypeRepository.save(subjectAnswer);
					}
					case SINGLE_CHOICE -> {
						FinalReviewResultAnswerObjects objectAnswer = new FinalReviewResultAnswerObjects(question_id);
						objectAnswer.addAnswer(answer);
						objectTypeRepository.save(objectAnswer);
					}
					case MULTIPLE_CHOICE -> {
						FinalReviewResultAnswerObjects objectAnswer = new FinalReviewResultAnswerObjects(question_id);
						objectAnswer.addAnswer(answer);
						objectTypeRepository.save(objectAnswer);
					}
					case RATING -> {
						FinalReviewResultAnswerRating ratingAnswer = new FinalReviewResultAnswerRating(question_id);
						ratingAnswer.addAnswer(answer);
						ratingTypeRepository.save(ratingAnswer);
					}
					case DROPDOWN -> {
						FinalReviewResultAnswerDropdown dropdownAnswer = new FinalReviewResultAnswerDropdown(
							question_id);
						dropdownAnswer.addAnswer(answer);
						dropdownTypeRepository.save(dropdownAnswer);
					}
					case HEXASTAT -> {
						FinalReviewResultAnswerHexStat hexStatAnswer = new FinalReviewResultAnswerHexStat(question_id);
						hexStatAnswer.addAnswer(answer);
						hexstatTypeRepository.save(hexStatAnswer);
					}
				}
			}
		}

		return new CreateFinalReviewResponse(userId);
	}

	public Boolean checkFinalResultStatus(Long reviewId) {
		validateReviewId(reviewId);

		int participantNums = participationRepository.findAllByReviewId(reviewId).size();
		int notSentFinalResultNums = finalReviewResultRepository.findAllByReviewIdAndStatus(reviewId, NOT_SENT).size();

		return participantNums == notSentFinalResultNums;
	}

	@Transactional
	public void sendFinalResultToUsers(Long reviewId) {
		validateReviewId(reviewId);

		List<Participation> participations = participationRepository.findAllByReviewId(reviewId);

		if (participations.isEmpty()) {
			throw new RangerException(NOT_FOUND_PARTICIPANTS);
		}

		Set<Long> userIds = new HashSet<>();

		for (Participation participation : participations) {
			Long participationId = participation.getId();
			List<ReplyTarget> replyTargets = replyTargetRepository.findAllByParticipationId(participationId);

			for (ReplyTarget replyTarget : replyTargets) {
				Long receiverId = replyTarget.getReceiverId();
				userIds.add(receiverId);
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

	private void validateReviewId(Long reviewId) {
		reviewRepository.findById(reviewId)
			.orElseThrow(() -> new RangerException(NOT_FOUND_REVIEW));
	}

	private void validateUserId(Long userId) {
		userRepository.findById(userId)
			.orElseThrow(() -> new RangerException(NOT_FOUND_USER));
	}
}
