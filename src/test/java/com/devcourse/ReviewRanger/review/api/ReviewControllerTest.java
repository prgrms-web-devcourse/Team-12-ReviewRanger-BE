package com.devcourse.ReviewRanger.review.api;

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

import com.devcourse.ReviewRanger.user.service.TestPrincipalDetailsService;
import com.devcourse.ReviewRanger.common.config.SecurityConfig;
import com.devcourse.ReviewRanger.common.jwt.JwtTokenProvider;
import com.devcourse.ReviewRanger.participation.application.ParticipationService;

import com.devcourse.ReviewRanger.question.dto.request.CreateQuestionRequest;
import com.devcourse.ReviewRanger.review.application.ReviewService;
import com.devcourse.ReviewRanger.review.domain.ReviewType;
import com.devcourse.ReviewRanger.review.dto.request.CreateReviewRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ReviewController.class)
@MockBean(JpaMetamodelMappingContext.class)
@Import(SecurityConfig.class)
class ReviewControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private ReviewService reviewService;

	@MockBean
	private ParticipationService participationService;

	@MockBean
	private JwtTokenProvider jwtTokenProvider;

	private TestPrincipalDetailsService testUserDetailsService;
	private UserDetails userDetails;

	@BeforeEach
	public void setUp(){
		testUserDetailsService = new TestPrincipalDetailsService();
		userDetails = testUserDetailsService.loadUserByUsername(USER_EMAIL);
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
		mvc.perform(post("/reviews")
				.content(objectMapper.writeValueAsString(createReviewRequest))
				.with(user(userDetails))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value(true))
			.andDo(print());

		verify(reviewService, times(1)).createReview(null,createReviewRequest);
	}
}