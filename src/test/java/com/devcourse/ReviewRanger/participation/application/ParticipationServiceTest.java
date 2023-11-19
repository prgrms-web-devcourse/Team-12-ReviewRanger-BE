package com.devcourse.ReviewRanger.participation.application;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devcourse.ReviewRanger.ReplyTarget.application.ReplyTargetService;
import com.devcourse.ReviewRanger.ReplyTarget.domain.ReplyTarget;
import com.devcourse.ReviewRanger.ReplyTarget.repository.ReplyTargetRepository;
import com.devcourse.ReviewRanger.common.exception.RangerException;
import com.devcourse.ReviewRanger.participation.domain.Participation;
import com.devcourse.ReviewRanger.participation.dto.request.SubmitParticipationRequest;
import com.devcourse.ReviewRanger.participation.dto.response.ParticipationResponse;
import com.devcourse.ReviewRanger.participation.dto.response.ReceiverResponse;
import com.devcourse.ReviewRanger.participation.repository.ParticipationRepository;
import com.devcourse.ReviewRanger.review.domain.Review;
import com.devcourse.ReviewRanger.review.domain.ReviewType;
import com.devcourse.ReviewRanger.review.repository.ReviewRepository;
import com.devcourse.ReviewRanger.user.domain.User;
import com.devcourse.ReviewRanger.user.repository.UserRepository;
import com.devcourse.ReviewRanger.user.service.UserFixture;

@ExtendWith(MockitoExtension.class)
class ParticipationServiceTest {

	@InjectMocks
	private ParticipationService participationService;

	@Mock
	private ParticipationRepository participationRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private ReplyTargetService replyTargetService;

	@Mock
	private ReplyTargetRepository replyTargetRepository;

	@Mock
	private ReviewRepository reviewRepository;

	@Test
	void 답변_참여_성공() {
		//given
		User user1 = UserFixture.SUYEON_FIXTURE.toEntity();

		SubmitParticipationRequest submitParticipationRequest = new SubmitParticipationRequest(1L,
			Lists.emptyList());
		Participation participation = new Participation(user1);

		given(participationRepository.findById(submitParticipationRequest.participationId())).willReturn(
			Optional.of(participation));

		//when
		participationService.submitReplies(submitParticipationRequest);

		//when
		Assertions.assertThat(participation.getIsAnswered()).isTrue();
	}

	@Test
	void 참여_조회_실패() {
		//given
		Long participationId = 9L;

		//when & then
		Assertions.assertThatThrownBy(() -> participationService.getByIdOrThrow(participationId))
			.isInstanceOf(RangerException.class)
			.hasMessage(NOT_FOUND_PARTICIPATION.getMessage());
	}

	@Test
	void 전체_응답자_조회() {
		//given
		User user1 = UserFixture.SPENCER_FIXTURE.toEntity();
		given(userRepository.findById(user1.getId())).willReturn(Optional.of(user1));

		Review review = new Review("피어 리뷰", "설명", ReviewType.PEER_REVIEW);
		review.setId(1L);
		given(reviewRepository.findById(1L)).willReturn(Optional.of(review));

		User suyeon = UserFixture.SUYEON_FIXTURE.toEntity();
		User beomchul = UserFixture.BEOMCHUL_FIXTURE.toEntity();
		User juwoong = UserFixture.JUWOONG_FIXTURE.toEntity();

		Participation participation1 = new Participation(suyeon);
		Participation participation2 = new Participation(beomchul);
		Participation participation3 = new Participation(juwoong);

		List<Participation> participations = List.of(participation1, participation2, participation3);

		given(participationRepository.findAllByReviewIdToDynamic(1L, null,
			null)).willReturn(participations);

		//when
		List<ParticipationResponse> responses = participationService.getAllParticipationOrThrow(1L, null,
			null);

		//then
		Assertions.assertThat(responses.get(0).user().name()).isEqualTo("장수연");
		Assertions.assertThat(responses.get(1).user().name()).isEqualTo("신범철");
		Assertions.assertThat(responses.get(2).user().name()).isEqualTo("김주웅");
		verify(participationRepository, times(1)).findAllByReviewIdToDynamic(1L, null, null);
	}

	@Test
	void 전체_수신자_조회() {
		//given
		User suyeon = UserFixture.SUYEON_FIXTURE.toEntity();
		User beomchul = UserFixture.BEOMCHUL_FIXTURE.toEntity();
		User juwoong = UserFixture.JUWOONG_FIXTURE.toEntity();

		Participation participation1 = new Participation(suyeon);
		participation1.setId(1L);
		Participation participation2 = new Participation(beomchul);
		participation2.setId(2L);
		Participation participation3 = new Participation(juwoong);
		participation3.setId(3L);
		participation1.answeredReview();

		List<Participation> participations = List.of(participation1, participation2, participation3);

		given(participationRepository.findByReviewId(1L)).willReturn(participations);

		ReplyTarget replyTarget1 = new ReplyTarget(beomchul, suyeon, 1L);
		ReplyTarget replyTarget2 = new ReplyTarget(juwoong, suyeon, 1L);

		List<ReplyTarget> replyTargetList = List.of(replyTarget1, replyTarget2);

		given(replyTargetRepository.findAllByParticipationIdToDynamic(1L, null)).willReturn(
			replyTargetList);

		//when
		List<ReceiverResponse> responses = participationService.getAllReceiver(1L, null);

		//then
		System.out.println(responses);
		Assertions.assertThat(responses.get(0).receiverName()).isEqualTo("신범철");
		Assertions.assertThat(responses.get(1).receiverName()).isEqualTo("김주웅");
		Assertions.assertThat(responses.get(0).responserCount()).isEqualTo(1);
		verify(replyTargetRepository, times(1)).findAllByParticipationIdToDynamic(1L, null);
	}
}
