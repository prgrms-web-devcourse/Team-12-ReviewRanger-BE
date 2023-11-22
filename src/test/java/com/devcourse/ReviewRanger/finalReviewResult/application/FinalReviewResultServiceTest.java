package com.devcourse.ReviewRanger.finalReviewResult.application;

import static com.devcourse.ReviewRanger.finalReviewResult.domain.FinalQuestionType.*;
import static com.devcourse.ReviewRanger.question.application.QuestionFixture.*;
import static com.devcourse.ReviewRanger.review.ReviewFixture.*;
import static com.devcourse.ReviewRanger.user.UserFixture.*;
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

import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalQuestion;
import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalQuestionType;
import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResult;
import com.devcourse.ReviewRanger.finalReviewResult.domain.Hexstat;
import com.devcourse.ReviewRanger.finalReviewResult.dto.CreateFinalReplyRequest;
import com.devcourse.ReviewRanger.finalReviewResult.dto.CreateFinalReviewRequest;
import com.devcourse.ReviewRanger.finalReviewResult.repository.FinalReviewResultRepository;
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

		// when
		when(userRepository.findById(any())).thenReturn(Optional.of(fakeUser));
		when(reviewRepository.findById(any())).thenReturn(Optional.of(fakeReview));
		when(questionRepository.findById(any())).thenReturn(Optional.of(fakeQuestion));
		when(finalReviewResultRepository.save(any(FinalReviewResult.class))).thenReturn(fakeFinalReviewResult);

		finalReviewResultService.createFinalReviewResult(request);

		// then
		verify(userRepository, times(1)).findById(any());
		verify(reviewRepository, times(1)).findById(any());
		verify(questionRepository, times(5)).findById(any());
		verify(finalReviewResultRepository, times(5)).save(any(FinalReviewResult.class));
	}
}
