package com.devcourse.ReviewRanger.reply.application;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
import com.devcourse.ReviewRanger.user.domain.User;

@ExtendWith(MockitoExtension.class)
class ReplyServiceTest {
	@InjectMocks
	private ReplyService replyService;

	@Mock
	private ReplyRepository replyRepository;

	private User user1;
	private User user2;

	@BeforeEach
	public void setup() {
		user1 = new User("수연", "tttttaa@naver.com", "asdf12345");
		user2 = new User("범철", "tttttbb@naver.com", "asdf12345");
	}

	@Test
	void 답변_저장_성공() {
		//given
		CreateReplyRequest createReplyRequest1 = new CreateReplyRequest(1L, 1L, null, null, null);
		CreateReplyRequest createReplyRequest2 = new CreateReplyRequest(1L, 2L, null, null, null);
		CreateReplyRequest createReplyRequest3 = new CreateReplyRequest(2L, null, "수연 -> 범철 주관식 답변", null, null);
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
		Reply reply = new Reply(2L, null, "수연 -> 범철 주관식 답변", null, null);
		replyRepository.save(reply);

		UpdateReplyRequest updateReplyRequest1 = new UpdateReplyRequest(1L, 2L, null, "수연 -> 범철 주관식 답변", null, null);
		List<UpdateReplyRequest> updateReplyRequestList = List.of(updateReplyRequest1);

		given(replyRepository.findById(updateReplyRequest1.id())).willReturn(Optional.of(reply));

		//when
		replyService.updateReply(updateReplyRequestList);

		//then
		verify(replyRepository, times(1)).save(any(Reply.class));
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
}
