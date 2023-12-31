package com.devcourse.ReviewRanger.reply.application;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devcourse.ReviewRanger.ReplyTarget.domain.ReplyTarget;
import com.devcourse.ReviewRanger.common.exception.RangerException;
import com.devcourse.ReviewRanger.reply.domain.Reply;
import com.devcourse.ReviewRanger.reply.dto.request.CreateReplyRequest;
import com.devcourse.ReviewRanger.reply.dto.request.UpdateReplyRequest;
import com.devcourse.ReviewRanger.reply.repository.ReplyRepository;
import com.devcourse.ReviewRanger.user.UserFixture;
import com.devcourse.ReviewRanger.user.domain.User;

@ExtendWith(MockitoExtension.class)
class ReplyServiceTest {

	@InjectMocks
	private ReplyService replyService;

	@Mock
	private ReplyRepository replyRepository;

	@Test
	void 답변_저장_성공() {
		//given
		User user1 = UserFixture.SUYEON_FIXTURE.toEntity();
		User user2 = UserFixture.BEOMCHUL_FIXTURE.toEntity();

		CreateReplyRequest createReplyRequest1 = new CreateReplyRequest(1L, true, 1L, null, null, null);
		CreateReplyRequest createReplyRequest2 = new CreateReplyRequest(1L, true, 2L, null, null, null);
		CreateReplyRequest createReplyRequest3 = new CreateReplyRequest(2L, true, null, "수연 -> 범철 주관식 답변", null, null);
		List<CreateReplyRequest> createReplyRequestList = List.of(createReplyRequest1, createReplyRequest2,
			createReplyRequest3);

		ReplyTarget replyTarget = new ReplyTarget(user2, user1, 1L);

		//when
		replyService.createReply(replyTarget, createReplyRequestList);

		//then
		verify(replyRepository, times(3)).save(any(Reply.class));
	}

	@Test
	void 답변_수정_성공() {
		//given
		User user1 = UserFixture.SUYEON_FIXTURE.toEntity();
		User user2 = UserFixture.BEOMCHUL_FIXTURE.toEntity();

		Reply reply = new Reply(2L, null, "수연 -> 범철 주관식 답변", null, null);
		replyRepository.save(reply);

		UpdateReplyRequest updateReplyRequest1 = new UpdateReplyRequest(2L, true, null, "수연 -> 범철 주관식 답변", null,
			null);
		List<UpdateReplyRequest> updateReplyRequestList = List.of(updateReplyRequest1);
		ReplyTarget replyTarget = new ReplyTarget(user2, user1, 1L);

		//when
		replyService.updateReply(replyTarget, updateReplyRequestList);

		//then
		verify(replyRepository, times(2)).save(any(Reply.class));
	}

	@Test
	void 답변_조회_실패() {
		//given
		Long replyId = 9L;

		//when & then
		Assertions.assertThatThrownBy(() -> replyService.getByIdOrThrow(replyId))
			.isInstanceOf(RangerException.class)
			.hasMessage(NOT_FOUND_REPLY.getMessage());
	}

	@Test
	void 답변_필수_질문_미답변_실패() {
		//given
		User user1 = UserFixture.SUYEON_FIXTURE.toEntity();
		User user2 = UserFixture.BEOMCHUL_FIXTURE.toEntity();

		CreateReplyRequest createReplyRequest1 = new CreateReplyRequest(1L, true, null, null, null, null);
		CreateReplyRequest createReplyRequest2 = new CreateReplyRequest(1L, true, null, null, null, null);
		CreateReplyRequest createReplyRequest3 = new CreateReplyRequest(2L, true, null, null, null, null);
		List<CreateReplyRequest> createReplyRequestList = List.of(createReplyRequest1, createReplyRequest2,
			createReplyRequest3);

		ReplyTarget replyTarget = new ReplyTarget(user2, user1, 1L);

		//when & Then
		Assertions.assertThatThrownBy(() -> replyService.createReply(replyTarget, createReplyRequestList))
			.isInstanceOf(RangerException.class)
			.hasMessage(MISSING_REQUIRED_QUESTION_REPLY.getMessage());
	}
}
