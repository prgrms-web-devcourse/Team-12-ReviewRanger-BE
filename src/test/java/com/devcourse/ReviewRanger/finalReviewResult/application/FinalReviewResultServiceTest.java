package com.devcourse.ReviewRanger.finalReviewResult.application;

import static com.devcourse.ReviewRanger.FinalQuestionFixture.*;
import static com.devcourse.ReviewRanger.ReplyTarget.ReplyTargetFixture.*;
import static com.devcourse.ReviewRanger.finalReviewResult.domain.FinalQuestionType.*;
import static com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResult.Status.*;
import static com.devcourse.ReviewRanger.participation.ParticipationFixture.*;
import static com.devcourse.ReviewRanger.question.application.QuestionFixture.*;
import static com.devcourse.ReviewRanger.review.ReviewFixture.*;
import static com.devcourse.ReviewRanger.user.UserFixture.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devcourse.ReviewRanger.ReplyTarget.domain.ReplyTarget;
import com.devcourse.ReviewRanger.ReplyTarget.repository.ReplyTargetRepository;
import com.devcourse.ReviewRanger.common.exception.RangerException;
import com.devcourse.ReviewRanger.finalReviewResult.FinalReviewResultFixture;
import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalQuestion;
import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalQuestionType;
import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResult;
import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResultAnswerDropdown;
import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResultAnswerHexStat;
import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResultAnswerObjects;
import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResultAnswerRating;
import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResultAnswerSubject;
import com.devcourse.ReviewRanger.finalReviewResult.domain.Hexstat;
import com.devcourse.ReviewRanger.finalReviewResult.dto.CreateFinalReplyRequest;
import com.devcourse.ReviewRanger.finalReviewResult.dto.CreateFinalReviewRequest;
import com.devcourse.ReviewRanger.finalReviewResult.dto.GetFinalReviewAnswerResponse;
import com.devcourse.ReviewRanger.finalReviewResult.dto.UpdateAnswerOfSubject;
import com.devcourse.ReviewRanger.finalReviewResult.repository.DropdownTypeRepository;
import com.devcourse.ReviewRanger.finalReviewResult.repository.FinalReviewResultRepository;
import com.devcourse.ReviewRanger.finalReviewResult.repository.HexstatTypeRepository;
import com.devcourse.ReviewRanger.finalReviewResult.repository.ObjectTypeRepository;
import com.devcourse.ReviewRanger.finalReviewResult.repository.RatingTypeRepository;
import com.devcourse.ReviewRanger.finalReviewResult.repository.SubjectTypeRepository;
import com.devcourse.ReviewRanger.participation.domain.Participation;
import com.devcourse.ReviewRanger.participation.repository.ParticipationRepository;
import com.devcourse.ReviewRanger.question.domain.Question;
import com.devcourse.ReviewRanger.question.repository.QuestionRepository;
import com.devcourse.ReviewRanger.review.domain.Review;
import com.devcourse.ReviewRanger.review.repository.ReviewRepository;
import com.devcourse.ReviewRanger.user.domain.User;
import com.devcourse.ReviewRanger.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class FinalReviewResultServiceTest {

	@InjectMocks
	private FinalReviewResultService finalReviewResultService;

	@Mock
	private FinalReviewResultRepository finalReviewResultRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private QuestionRepository questionRepository;

	@Mock
	private ReviewRepository reviewRepository;

	@Mock
	private ParticipationRepository participationRepository;

	@Mock
	private ReplyTargetRepository replyTargetRepository;

	@Mock
	private SubjectTypeRepository subjectTypeRepository;

	@Mock
	private ObjectTypeRepository objectTypeRepository;

	@Mock
	private RatingTypeRepository ratingTypeRepository;

	@Mock
	private DropdownTypeRepository dropdownTypeRepository;

	@Mock
	private HexstatTypeRepository hexstatTypeRepository;

	@Test
	@DisplayName("최종 리뷰 결과 생성 성공 테스트")
	void 최종_리뷰_생성_성공() {
		// given
		Long userId = 1L;
		Long reviewId = 1L;
		List<Long> questionIds = List.of(1L, 2L, 3L, 4L, 5L);

		List<CreateFinalReplyRequest> replies = new ArrayList<>();
		Map<FinalQuestionType, List<Object>> questionTypesAndAnswers
			= Map.of(
			SUBJECTIVE, List.of("주관식 답변입니다."),
			SINGLE_CHOICE, List.of("파이리", "꼬부기", "치코리타"),
			RATING, List.of(1.0, 2.0, 3.0, 4.0),
			DROPDOWN, List.of("드롭다운 답변1", "드롭다운 답변2", "드롭다운 답변2"),
			HEXASTAT, List.of(
				new Hexstat("바보", 2.0),
				new Hexstat("말미잘", 3.0),
				new Hexstat("해삼", 4.0),
				new Hexstat("똥꼴래", 5.0),
				new Hexstat("파스타", 6.0),
				new Hexstat("신비력", 1.0)
			)
		);
		Set<FinalQuestionType> questionTypes = questionTypesAndAnswers.keySet();

		int i = 0;
		for (FinalQuestionType questionType : questionTypes) {
			Long questionId = questionIds.get(i);
			String questionTitle = "질문 제목" + questionId;
			CreateFinalReplyRequest createFinalReplyRequest = new CreateFinalReplyRequest(
				questionId,
				questionTitle,
				questionType,
				questionTypesAndAnswers.get(questionType)
			);
			replies.add(createFinalReplyRequest);
			i++;
		}

		CreateFinalReviewRequest request = new CreateFinalReviewRequest(
			userId,
			"장수연",
			reviewId,
			"1차 피어리뷰 입니다.",
			"마감 기한은 내일까지 입니다.",
			replies
		);

		User fakeUser = SUYEON_FIXTURE.toEntity();
		Question fakeQuestion = BASIC_QUESTION.toEntity();
		Review fakeReview = BASIC_REVIEW.toEntity();
		FinalReviewResult fakeFinalReviewResult = request.toEntity();

		for (CreateFinalReplyRequest reply : replies) {
			FinalQuestion finalQuestion = reply.toEntity();
			fakeFinalReviewResult.addQuestions(finalQuestion);
		}

		when(userRepository.findById(any())).thenReturn(Optional.of(fakeUser));
		when(reviewRepository.findById(any())).thenReturn(Optional.of(fakeReview));
		when(questionRepository.findById(any())).thenReturn(Optional.of(fakeQuestion));
		when(finalReviewResultRepository.save(any(FinalReviewResult.class))).thenReturn(fakeFinalReviewResult);

		// when
		finalReviewResultService.createFinalReviewResult(request);

		// then
		verify(userRepository, times(1)).findById(any());
		verify(reviewRepository, times(1)).findById(any());
		verify(questionRepository, times(5)).findById(any());
		verify(finalReviewResultRepository, times(5)).save(any(FinalReviewResult.class));
	}

	@Test
	@DisplayName("최종 리뷰 결과 중복 생성 방지 테스트")
	void 최종_리뷰_중복생성_방지() {
		// given
		Long userId = 1L;
		Long reviewId = 1L;
		List<Long> questionIds = List.of(1L, 2L, 3L, 4L, 5L);

		List<CreateFinalReplyRequest> replies = new ArrayList<>();
		Map<FinalQuestionType, List<Object>> questionTypesAndAnswers
			= Map.of(
			SUBJECTIVE, List.of("주관식 답변입니다."),
			SINGLE_CHOICE, List.of("파이리", "꼬부기", "치코리타"),
			RATING, List.of(1.0, 2.0, 3.0, 4.0),
			DROPDOWN, List.of("드롭다운 답변1", "드롭다운 답변2", "드롭다운 답변2"),
			HEXASTAT, List.of(
				new Hexstat("바보", 2.0),
				new Hexstat("말미잘", 3.0),
				new Hexstat("해삼", 4.0),
				new Hexstat("똥꼴래", 5.0),
				new Hexstat("파스타", 6.0),
				new Hexstat("신비력", 1.0)
			)
		);
		Set<FinalQuestionType> questionTypes = questionTypesAndAnswers.keySet();

		int i = 0;
		for (FinalQuestionType questionType : questionTypes) {
			Long questionId = questionIds.get(i);
			String questionTitle = "질문 제목" + questionId;
			CreateFinalReplyRequest createFinalReplyRequest = new CreateFinalReplyRequest(
				questionId,
				questionTitle,
				questionType,
				questionTypesAndAnswers.get(questionType)
			);
			replies.add(createFinalReplyRequest);
			i++;
		}

		CreateFinalReviewRequest request = new CreateFinalReviewRequest(
			userId,
			"장수연",
			reviewId,
			"1차 피어리뷰 입니다.",
			"마감 기한은 내일까지 입니다.",
			replies
		);

		User fakeUser = SUYEON_FIXTURE.toEntity();
		Review fakeReview = BASIC_REVIEW.toEntity();
		FinalReviewResult fakeFinalReviewResult = request.toEntity();

		for (CreateFinalReplyRequest reply : replies) {
			FinalQuestion finalQuestion = reply.toEntity();
			fakeFinalReviewResult.addQuestions(finalQuestion);
		}

		when(userRepository.findById(any())).thenReturn(Optional.of(fakeUser));
		when(reviewRepository.findById(any())).thenReturn(Optional.of(fakeReview));
		when(finalReviewResultRepository.findByReviewIdAndUserIdAndStatus(userId, reviewId, NOT_SENT)).
			thenReturn(Optional.of(fakeFinalReviewResult));

		// when
		finalReviewResultService.createFinalReviewResult(request);

		// then
		verify(userRepository, times(1)).findById(any());
		verify(reviewRepository, times(1)).findById(any());
		verify(finalReviewResultRepository, times(0)).save(any(FinalReviewResult.class));
	}

	@Test
	void 주관식_결과_업데이트_성공() {
		// given
		FinalReviewResultAnswerSubject answerSubject = new FinalReviewResultAnswerSubject(1L, 1L);
		String updateAnswer = "주관식 답변을 정제한 내용입니다. 이것을 다시 저장할게요!!";
		UpdateAnswerOfSubject updateAnswerOfSubject = new UpdateAnswerOfSubject(1L, 1L, 1L, updateAnswer);
		Review mockReview = mock(Review.class);

		when(reviewRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mockReview));
		when(subjectTypeRepository.findByQuestionIdAndUserId(1L, 1L)).thenReturn(Optional.of(answerSubject));

		// when
		finalReviewResultService.updateAnswerOfSubject(updateAnswerOfSubject);

		// then
		assertEquals(answerSubject.getSubjects(), updateAnswer);
	}

	@Test
	void 최종_리뷰_전송_성공() {
		// given
		Long reviewId = 1L;

		Review fakeReview = BASIC_REVIEW.toEntity();
		User fakeUser = SUYEON_FIXTURE.toEntity();
		Participation fakeParticipation = PARTICIPATION_FIXTURE.toEntity();
		List<Participation> participationList = List.of(fakeParticipation);
		List<ReplyTarget> replyTargets = List.of(BASIC_FIXTURE.toEntity());
		FinalReviewResult fakeFinalReviewResult = FinalReviewResultFixture.BASIC_FIXTURE.toEntity();

		when(reviewRepository.findById(any())).thenReturn(Optional.of(fakeReview));
		when(participationRepository.findAllByReviewId(any())).thenReturn(participationList);
		when(replyTargetRepository.findAllByParticipationId(any())).thenReturn(replyTargets);
		when(userRepository.findById(any())).thenReturn(Optional.of(fakeUser));
		when(finalReviewResultRepository.findByReviewIdAndUserIdAndStatus(any(), any(), any(
			FinalReviewResult.Status.class))).thenReturn(Optional.of(fakeFinalReviewResult));

		// when
		finalReviewResultService.sendFinalResultToUsers(reviewId);

		// then
		verify(reviewRepository, times(1)).findById(any());
		assertEquals(fakeFinalReviewResult.getStatus(), SENT);
	}

	@Test
	void 최종_리뷰_결과_상세조회_성공() {
		// given
		Long userId = 1L;
		Long finalReviewId = 1L;
		FinalReviewResult fakeFinalReviewResult = FinalReviewResultFixture.BASIC_FIXTURE.toEntity();

		when(finalReviewResultRepository.findById(finalReviewId)).thenReturn(Optional.of(fakeFinalReviewResult));

		// when
		finalReviewResultService.getFinalReviewResultInfo(userId, finalReviewId);

		// then
		verify(finalReviewResultRepository, times(1)).findById(any());
	}

	@Test
	void 최종_리뷰_결과_상세조회_실패() {
		// given
		Long userId = 2L;
		Long finalReviewId = 1L;
		FinalReviewResult fakeFinalReviewResult = FinalReviewResultFixture.BASIC_FIXTURE.toEntity();

		when(finalReviewResultRepository.findById(finalReviewId)).thenReturn(Optional.of(fakeFinalReviewResult));

		// when & then
		assertThrows(RangerException.class,
			() -> finalReviewResultService.getFinalReviewResultInfo(userId, finalReviewId)
		);
	}

	@Test
	void 최종_리뷰_결과_답변_상세조회_성공() {
		// given
		Long finalReviewId = 1L;
		List<FinalQuestion> fakeFinalQuestions = List.of(
			ONCE_QUESTION.toEntity(),
			TWICE_QUESTION.toEntity(),
			THREE_QUESTION.toEntity(),
			FOUR_QUESTION.toEntity(),
			FIVE_QUESTION.toEntity(),
			SIX_QUESTION.toEntity()
		);
		FinalReviewResult fakeFinalReviewResult = FinalReviewResultFixture.BASIC_FIXTURE.toEntity(fakeFinalQuestions);

		FinalReviewResultAnswerSubject subjectAnswer = new FinalReviewResultAnswerSubject(1L, 1L);
		FinalReviewResultAnswerObjects objectAnswer = new FinalReviewResultAnswerObjects(1L, 2L);
		FinalReviewResultAnswerRating ratingAnswer = new FinalReviewResultAnswerRating(1L, 3L);
		FinalReviewResultAnswerDropdown dropdownAnswer = new FinalReviewResultAnswerDropdown(1L, 4L);
		FinalReviewResultAnswerHexStat hexstatAnswer = new FinalReviewResultAnswerHexStat(1L, 5L);

		List<FinalReviewResultAnswerObjects> objectAnswers = List.of(objectAnswer);
		List<FinalReviewResultAnswerRating> ratingAnswers = List.of(ratingAnswer);
		List<FinalReviewResultAnswerDropdown> dropdownAnswers = List.of(dropdownAnswer);
		List<FinalReviewResultAnswerHexStat> hexstatAnswers = List.of(hexstatAnswer);

		when(finalReviewResultRepository.findById(finalReviewId)).thenReturn(Optional.of(fakeFinalReviewResult));
		when(subjectTypeRepository.findByQuestionIdAndUserId(any(), any())).thenReturn(Optional.of(subjectAnswer));
		when(objectTypeRepository.findAllByQuestionIdAndUserId(any(), any())).thenReturn(objectAnswers);
		when(ratingTypeRepository.findAllByQuestionIdAndUserId(any(), any())).thenReturn(ratingAnswers);
		when(dropdownTypeRepository.findAllByQuestionIdAndUserId(any(), any())).thenReturn(dropdownAnswers);
		when(hexstatTypeRepository.findAllByQuestionIdAndUserId(any(), any())).thenReturn(hexstatAnswers);

		// when
		List<GetFinalReviewAnswerResponse> getFinalReviewAnswerResponses = finalReviewResultService.getFinalReviewAnswerList(
			finalReviewId);

		// then
		verify(finalReviewResultRepository, times(1)).findById(finalReviewId);
		assertEquals(getFinalReviewAnswerResponses.size(), 6);
	}
}
