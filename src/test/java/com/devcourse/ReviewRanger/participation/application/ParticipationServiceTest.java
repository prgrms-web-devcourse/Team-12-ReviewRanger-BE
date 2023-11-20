package com.devcourse.ReviewRanger.participation.application;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devcourse.ReviewRanger.ReplyTarget.application.ReplyTargetService;
import com.devcourse.ReviewRanger.ReplyTarget.repository.ReplyTargetRepository;
import com.devcourse.ReviewRanger.common.exception.RangerException;
import com.devcourse.ReviewRanger.participation.domain.Participation;
import com.devcourse.ReviewRanger.participation.dto.request.SubmitParticipationRequest;
import com.devcourse.ReviewRanger.participation.repository.ParticipationRepository;
import com.devcourse.ReviewRanger.user.UserFixture;
import com.devcourse.ReviewRanger.user.domain.User;
import com.devcourse.ReviewRanger.user.repository.UserRepository;

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
}
