package com.devcourse.ReviewRanger.ReplyTarget.api;

import static java.time.LocalDateTime.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import com.devcourse.ReviewRanger.ReplyTarget.application.ReplyTargetService;
import com.devcourse.ReviewRanger.ReplyTarget.dto.response.ReplyTargetResponse;
import com.devcourse.ReviewRanger.common.config.SecurityConfig;
import com.devcourse.ReviewRanger.common.jwt.JwtTokenProvider;
import com.devcourse.ReviewRanger.participation.application.ParticipationService;
import com.devcourse.ReviewRanger.question.dto.response.GetQuestionOptionResponse;
import com.devcourse.ReviewRanger.reply.dto.response.ReplyResponse;
import com.devcourse.ReviewRanger.review.api.ReviewController;
import com.devcourse.ReviewRanger.review.application.ReviewService;
import com.devcourse.ReviewRanger.user.dto.UserResponse;

@WebMvcTest(ReviewController.class)
@MockBean(JpaMetamodelMappingContext.class)
@Import(SecurityConfig.class)
class ReplyTargetRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private JwtTokenProvider jwtTokenProvider;

	@MockBean
	private ReviewService reviewService;

	@MockBean
	private ParticipationService participationService;

	@MockBean
	private ReplyTargetService replyTargetService;

	private ReplyTargetResponse response;

	@BeforeEach
	public void setup() {
		LocalDateTime now = now();

		UserResponse 수연 = new UserResponse(1L, "aaaatt11@naver.com", "장수연", now,
			now);
		UserResponse 범철 = new UserResponse(2L, "aaaatt22@naver.com", "신범철", now,
			now());

		GetQuestionOptionResponse 파이리 = new GetQuestionOptionResponse(1L, "파이리", now(),
			now());
		GetQuestionOptionResponse 꼬부기 = new GetQuestionOptionResponse(2L, "꼬부기", now(),
			now());
		GetQuestionOptionResponse 육각1 = new GetQuestionOptionResponse(3L, "육각1", now(),
			now());
		GetQuestionOptionResponse 육각2 = new GetQuestionOptionResponse(4L, "육각1", now(),
			now());
		GetQuestionOptionResponse 육각3 = new GetQuestionOptionResponse(5L, "육각1", now(),
			now());
		GetQuestionOptionResponse 육각4 = new GetQuestionOptionResponse(6L, "육각1", now(),
			now());
		GetQuestionOptionResponse 육각5 = new GetQuestionOptionResponse(7L, "육각1", now(),
			now());
		GetQuestionOptionResponse 육각6 = new GetQuestionOptionResponse(8L, "육각1", now(),
			now());

		ReplyResponse replyResponse1 = new ReplyResponse(1L, 1L, 파이리, null, null, null, 1L, now(),
			now());
		ReplyResponse replyResponse2 = new ReplyResponse(2L, 1L, 꼬부기, null, null, null, 1L, now(),
			now());
		ReplyResponse replyResponse3 = new ReplyResponse(3L, 2L, null, "수연 -> 범철 주관식 답변", null, null, 1L, now(),
			now());
		ReplyResponse replyResponse4 = new ReplyResponse(4L, 3L, 육각1, null, null, 5, 1L, now(),
			now());
		ReplyResponse replyResponse5 = new ReplyResponse(5L, 3L, 육각2, null, null, 4, 1L, now(),
			now());
		ReplyResponse replyResponse6 = new ReplyResponse(6L, 3L, 육각3, null, null, 3, 1L, now(),
			now());
		ReplyResponse replyResponse7 = new ReplyResponse(7L, 3L, 육각4, null, null, 2, 1L, now(),
			now());
		ReplyResponse replyResponse8 = new ReplyResponse(8L, 3L, 육각5, null, null, 1, 1L, now(),
			now());
		ReplyResponse replyResponse9 = new ReplyResponse(9L, 3L, 육각6, null, null, 3, 1L, now(),
			now());
		ReplyResponse replyResponse10 = new ReplyResponse(10L, 4L, null, null, 5.0, null, 1L, now(),
			now());

		response = new ReplyTargetResponse(1L, 범철, 수연, 1L,
			List.of(replyResponse1, replyResponse2, replyResponse3, replyResponse4, replyResponse5, replyResponse6,
				replyResponse7, replyResponse8, replyResponse9, replyResponse10), now, now);
	}

	@Test
	void 작성자_답변_조회_성공() throws Exception {

		given(replyTargetService.getAllNonEmptyRepliesByResponser(1L, 1L)).willReturn(List.of(response));
		UserDetails userDetails = null;

		mockMvc.perform(get("/reviews/{reviewId}/responser/{responserId}/creator", 1L, 1L)
				.contentType(MediaType.APPLICATION_JSON)
				.with(user(userDetails)))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	void 수신자_답변_조회_성공() throws Exception {
		given(replyTargetService.getAllNonEmptyRepliesByReceiver(1L, 2L)).willReturn(List.of(response));
		UserDetails userDetails = null;

		mockMvc.perform(get("/reviews/{reviewId}/receiver/{receiverId}", 1L, 2L)
				.contentType(MediaType.APPLICATION_JSON)
				.with(user(userDetails)))
			.andExpect(status().isOk())
			.andDo(print());
	}

}
