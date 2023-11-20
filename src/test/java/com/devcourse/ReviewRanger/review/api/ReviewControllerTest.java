package com.devcourse.ReviewRanger.review.api;

import static java.time.LocalDateTime.*;
import static org.mockito.BDDMockito.*;
import static com.devcourse.ReviewRanger.user.service.TestPrincipalDetailsService.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.devcourse.ReviewRanger.user.service.TestPrincipalDetailsService;
import com.devcourse.ReviewRanger.common.config.SecurityConfig;
import com.devcourse.ReviewRanger.common.jwt.JwtTokenProvider;
import com.devcourse.ReviewRanger.participation.application.ParticipationService;
import com.devcourse.ReviewRanger.participation.domain.ReviewStatus;
import com.devcourse.ReviewRanger.participation.dto.response.ParticipationResponse;

import com.devcourse.ReviewRanger.question.dto.request.CreateQuestionRequest;
import com.devcourse.ReviewRanger.review.application.ReviewService;
import com.devcourse.ReviewRanger.review.domain.ReviewType;
import com.devcourse.ReviewRanger.review.dto.request.CreateReviewRequest;
import com.devcourse.ReviewRanger.review.dto.response.ReviewResponse;
import com.devcourse.ReviewRanger.user.dto.UserResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ReviewController.class)
@MockBean(JpaMetamodelMappingContext.class)
@Import(SecurityConfig.class)
class ReviewControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private ReviewService reviewService;

	@MockBean
	private ParticipationService participationService;
  
  private TestPrincipalDetailsService testUserDetailsService;
	private UserDetails userDetails;

	@BeforeEach
	public void setUp(){
		testUserDetailsService = new TestPrincipalDetailsService();
		userDetails = testUserDetailsService.loadUserByUsername(USER_EMAIL);
	}

	@Test
	void 수신자_전체_조회() throws Exception {
		ReceiverResponse 범철 = new ReceiverResponse(2L, "범철", 1);
		ReceiverResponse 주웅 = new ReceiverResponse(3L, "주웅", 1);

		List<ReceiverResponse> responses = List.of(범철, 주웅);

		given(participationService.getAllReceiver(1L, null)).willReturn(responses);

		mockMvc.perform(MockMvcRequestBuilders.get("/reviews/{id}/receiver", 1L)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	void 작성자_전체_조회() throws Exception {
		LocalDateTime now = now();

		UserResponse 스펜서 = new UserResponse(
			3L,
			"aaaatt33@naver.com",
			"스펜서",
			now,
			now
		);

		ReviewResponse reviewResponse = new ReviewResponse(
			1L,
			스펜서,
			"피어 리뷰",
			"설명",
			ReviewType.PEER_REVIEW,
			now,
			ReviewStatus.PROCEEDING,
			now,
			now
		);

		UserResponse 수연 = new UserResponse(
			1L,
			"aaaatt11@naver.com",
			"수연",
			now,
			now
		);

		ParticipationResponse participationResponse = new ParticipationResponse(1L,
			reviewResponse,
			수연,
			false,
			ReviewStatus.PROCEEDING,
			null,
			now,
			now
		);

		List<ParticipationResponse> responses = List.of(participationResponse);

		given(participationService.getAllParticipationOrThrow(1L, null, null)).willReturn(responses);

		mockMvc.perform(MockMvcRequestBuilders.get("/reviews/{id}/responser", 1L)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print());
	}
  
  @Test
	void 리뷰_생성_성공() throws Exception {
		// given
		String title = "테스트 리뷰";
		String description = "이 리뷰는 테스트입니다.";
		ReviewType type = ReviewType.PEER_REVIEW;
		List<CreateQuestionRequest> createQuestionRequests = new ArrayList<>();
		List<Long> responserIds = new ArrayList<>();

		CreateReviewRequest createReviewRequest = new CreateReviewRequest(
			title,
			description,
			type,
			createQuestionRequests,
			responserIds
		);

		when(reviewService.createReview(null,createReviewRequest)).thenReturn(true);

		// when
		// then
		mockMvc.perform(post("/reviews")
				.content(objectMapper.writeValueAsString(createReviewRequest))
				.with(user(userDetails))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value(true))
			.andDo(print());

		verify(reviewService, times(1)).createReview(null,createReviewRequest);
	}
}
