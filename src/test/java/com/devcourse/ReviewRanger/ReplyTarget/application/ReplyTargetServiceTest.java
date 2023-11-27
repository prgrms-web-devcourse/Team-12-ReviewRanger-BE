package com.devcourse.ReviewRanger.ReplyTarget.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devcourse.ReviewRanger.ReplyTarget.domain.ReplyTarget;
import com.devcourse.ReviewRanger.ReplyTarget.dto.response.ReplyTargetResponse;
import com.devcourse.ReviewRanger.ReplyTarget.fixture.ReplyFixture;
import com.devcourse.ReviewRanger.ReplyTarget.repository.ReplyTargetRepository;
import com.devcourse.ReviewRanger.question.domain.QuestionOption;
import com.devcourse.ReviewRanger.question.repository.QuestionOptionRepository;
import com.devcourse.ReviewRanger.reply.domain.Reply;
import com.devcourse.ReviewRanger.user.UserFixture;
import com.devcourse.ReviewRanger.user.domain.User;

@ExtendWith(MockitoExtension.class)
class ReplyTargetServiceTest {

	@InjectMocks
	private ReplyTargetService replyTargetService;

	@Mock
	private ReplyTargetRepository replyTargetRepository;

	@Mock
	private QuestionOptionRepository questionOptionRepository;

	private User user1;
	private User user2;

	@BeforeEach
	public void setup() {
		user1 = UserFixture.SUYEON_FIXTURE.toEntity();
		user2 = UserFixture.BEOMCHUL_FIXTURE.toEntity();
	}

	@Test
	void 작성자별_답변_조회_성공() {
		//given
		Reply subjectReplyEntity = ReplyFixture.SUBJECT_REPLY.toEntity();

		ReplyTarget replyTarget = new ReplyTarget(user2, user1, 1L);
		subjectReplyEntity.assignReviewedTarget(replyTarget);

		List<ReplyTarget> replyTargetList = List.of(replyTarget);

		given(replyTargetRepository.findAllByReviewIdAndResponserId(1L, 1L)).willReturn(replyTargetList);

		//when
		List<ReplyTargetResponse> responses = replyTargetService.getAllRepliesByResponser(1L, 1L);

		//then
		assertThat(responses.get(0).responser().name()).isEqualTo("장수연");
		assertThat(responses.get(0).receiver().name()).isEqualTo("신범철");
		assertThat(responses.get(0).replies().get(0).answerText()).isEqualTo("주관식 답변");
		verify(replyTargetRepository, times(1)).findAllByReviewIdAndResponserId(any(Long.class), any(Long.class));
	}

	@Test
	void 수신자별_답변_조회_성공() {
		//given
		Reply subjectReplyEntity = ReplyFixture.SUBJECT_REPLY.toEntity();
		Reply objectReplyEntity = ReplyFixture.OBJECT_REPLY1.toEntity();

		QuestionOption questionOption = new QuestionOption("파이리");

		ReplyTarget replyTarget = new ReplyTarget(user2, user1, 1L);
		subjectReplyEntity.assignReviewedTarget(replyTarget);
		objectReplyEntity.assignReviewedTarget(replyTarget);

		List<ReplyTarget> replyTargetList = List.of(replyTarget);

		given(replyTargetRepository.findAllByReviewIdAndReceiverId(1L, 2L)).willReturn(replyTargetList);
		given(questionOptionRepository.findById(1L)).willReturn(Optional.of(questionOption));

		//when
		List<ReplyTargetResponse> responses = replyTargetService.getAllNonEmptyRepliesByReceiver(1L, 2L);

		//then
		assertThat(responses.get(0).responser().name()).isEqualTo("장수연");
		assertThat(responses.get(0).receiver().name()).isEqualTo("신범철");
		assertThat(responses.get(0).replies().get(0).answerText()).isEqualTo("주관식 답변");
		assertThat(responses.get(0).replies().get(1).questionOption().optionName()).isEqualTo("파이리");

		verify(replyTargetRepository, times(1)).findAllByReviewIdAndReceiverId(any(Long.class), any(Long.class));
	}
}
