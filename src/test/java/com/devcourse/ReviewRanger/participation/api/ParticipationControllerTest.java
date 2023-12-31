package com.devcourse.ReviewRanger.participation.api;

import static com.devcourse.ReviewRanger.user.UserFixture.*;
import static org.assertj.core.api.BDDAssumptions.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import com.devcourse.ReviewRanger.ReplyTarget.dto.request.CreateReplyTargetRequest;
import com.devcourse.ReviewRanger.ReplyTarget.dto.request.UpdateReplyTargetRequest;
import com.devcourse.ReviewRanger.auth.domain.UserPrincipal;
import com.devcourse.ReviewRanger.common.config.SecurityConfig;
import com.devcourse.ReviewRanger.common.jwt.JwtTokenProvider;
import com.devcourse.ReviewRanger.participation.application.ParticipationService;
import com.devcourse.ReviewRanger.participation.domain.Participation;
import com.devcourse.ReviewRanger.participation.dto.request.SubmitParticipationRequest;
import com.devcourse.ReviewRanger.participation.dto.request.UpdateParticipationRequest;
import com.devcourse.ReviewRanger.participation.dto.response.GetParticipationResponse;
import com.devcourse.ReviewRanger.question.domain.QuestionType;
import com.devcourse.ReviewRanger.question.dto.request.CreateQuestionOptionRequest;
import com.devcourse.ReviewRanger.question.dto.request.CreateQuestionRequest;
import com.devcourse.ReviewRanger.reply.application.ReplyService;
import com.devcourse.ReviewRanger.reply.dto.request.CreateReplyRequest;
import com.devcourse.ReviewRanger.reply.dto.request.UpdateReplyRequest;
import com.devcourse.ReviewRanger.review.application.ReviewService;
import com.devcourse.ReviewRanger.review.domain.ReviewType;
import com.devcourse.ReviewRanger.review.dto.request.CreateReviewRequest;
import com.devcourse.ReviewRanger.user.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ParticipationController.class)
@MockBean(JpaMetamodelMappingContext.class)
@Import(SecurityConfig.class)
class ParticipationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private JwtTokenProvider jwtTokenProvider;

	@MockBean
	private ReplyService replyService;

	@MockBean
	private ParticipationService participationService;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private ReviewService reviewService;

	@BeforeEach
	public void setup() {
		User user = SPENCER_FIXTURE.toEntity();

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
			user, null, Collections.emptyList());
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);

		CreateQuestionOptionRequest createQuestionOptionRequest1 = new CreateQuestionOptionRequest("옵션1");
		CreateQuestionOptionRequest createQuestionOptionRequest2 = new CreateQuestionOptionRequest("옵션2");
		List<CreateQuestionOptionRequest> createQuestionOptionRequestList = List.of(createQuestionOptionRequest1,
			createQuestionOptionRequest2);

		CreateQuestionRequest createQuestionRequest1 = new CreateQuestionRequest("질문1", "질문 설명",
			QuestionType.MULTIPLE_CHOICE,
			true, createQuestionOptionRequestList);
		CreateQuestionRequest createQuestionRequest2 = new CreateQuestionRequest("질문2", "질문 설명",
			QuestionType.SUBJECTIVE, true,
			Lists.emptyList());
		List<CreateQuestionRequest> createQuestionRequestList = List.of(createQuestionRequest1, createQuestionRequest2);

		CreateReviewRequest createReviewRequest = new CreateReviewRequest("피어 리뷰", "설명", ReviewType.PEER_REVIEW,
			createQuestionRequestList, List.of(1L, 2L, 3L));

		given(reviewService.createReview(4L, createReviewRequest));
		verify(reviewService, times(1)).createReview(4L, createReviewRequest);
	}

	@Test
	void 답변_저장() throws Exception {
		//given
		CreateReplyRequest createReplyRequest1 = new CreateReplyRequest(1L, true, 1L, null, null, null);
		CreateReplyRequest createReplyRequest2 = new CreateReplyRequest(1L, true, 2L, null, null, null);
		CreateReplyRequest createReplyRequest3 = new CreateReplyRequest(2L, true, null, "수연 -> 범철 주관식 답변", null, null);
		List<CreateReplyRequest> createReplyRequestList1 = List.of(createReplyRequest1, createReplyRequest2,
			createReplyRequest3);

		CreateReplyTargetRequest createReplyTargetRequest1 = new CreateReplyTargetRequest(2L, 1L,
			createReplyRequestList1);

		CreateReplyRequest createReplyRequest4 = new CreateReplyRequest(1L, true, 1L, null, null, null);
		CreateReplyRequest createReplyRequest5 = new CreateReplyRequest(1L, true, 2L, null, null, null);
		CreateReplyRequest createReplyRequest6 = new CreateReplyRequest(2L, true, null, "수연 -> 주웅 주관식 답변", null, null);
		List<CreateReplyRequest> createReplyRequestList2 = List.of(createReplyRequest4, createReplyRequest5,
			createReplyRequest6);

		CreateReplyTargetRequest createReplyTargetRequest2 = new CreateReplyTargetRequest(3L, 1L,
			createReplyRequestList2);

		List<CreateReplyTargetRequest> createReplyTargetRequestList = List.of(createReplyTargetRequest1,
			createReplyTargetRequest2);

		SubmitParticipationRequest submitParticipationRequest = new SubmitParticipationRequest(1L,
			createReplyTargetRequestList);

		this.mockMvc.perform(post("/participations")
				.content(objectMapper.writeValueAsString(submitParticipationRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andDo(print());
	}

	@Test
	void 답변_수정() throws Exception {
		//given
		UpdateReplyRequest updateReplyRequest1 = new UpdateReplyRequest(1L, true, 1L, null, null, null);
		UpdateReplyRequest updateReplyRequest2 = new UpdateReplyRequest(1L, true, 2L, null, null, null);
		UpdateReplyRequest updateReplyRequest3 = new UpdateReplyRequest(2L, true, null, "수연 -> 범철 주관식 답변", null,
			null);
		List<UpdateReplyRequest> updateReplyRequestList = List.of(updateReplyRequest1, updateReplyRequest2,
			updateReplyRequest3);

		UpdateReplyTargetRequest updateReplyTargetRequest1 = new UpdateReplyTargetRequest(1L, 2L, 1L,
			updateReplyRequestList);

		UpdateReplyRequest updateReplyRequest4 = new UpdateReplyRequest(1L, true, 1L, null, null, null);
		UpdateReplyRequest updateReplyRequest5 = new UpdateReplyRequest(1L, true, 2L, null, null, null);
		UpdateReplyRequest updateReplyRequest6 = new UpdateReplyRequest(2L, true, null, "수연 -> 주웅 주관식 답변", null,
			null);
		List<UpdateReplyRequest> createReplyRequestList2 = List.of(updateReplyRequest4, updateReplyRequest5,
			updateReplyRequest6);

		UpdateReplyTargetRequest updateReplyTargetRequest2 = new UpdateReplyTargetRequest(2L, 3L, 1L,
			createReplyRequestList2);

		List<UpdateReplyTargetRequest> updateReplyTargetRequestList = List.of(updateReplyTargetRequest1,
			updateReplyTargetRequest2);

		UpdateParticipationRequest updateParticipationRequest = new UpdateParticipationRequest(1L,
			updateReplyTargetRequestList);

		this.mockMvc.perform(put("/participations")
				.content(objectMapper.writeValueAsString(updateParticipationRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	void 잠여_전체조회_성공() throws Exception {
		// given
		Long cursorId = 1L;
		Integer size = 12;

		String title = "REVIEW_TITLE";

		Pageable pageable = PageRequest.of(0, size);
		Boolean hasNext = false;

		Participation participation = new Participation(SUYEON_FIXTURE.toEntity());
		GetParticipationResponse getParticipationResponse = new GetParticipationResponse(participation, title);

		SliceImpl<GetParticipationResponse> getReviewResponses = new SliceImpl<>(
			List.of(getParticipationResponse),
			pageable,
			hasNext
		);

		when(participationService.getAllParticipationsByResponserOfCursorPaging(cursorId, null, pageable))
			.thenReturn(getReviewResponses);
		UserDetails userDetails = new UserPrincipal(SUYEON_FIXTURE.toEntity());

		// when
		// then
		mockMvc.perform(get("/participations")
				.param("cursorId", "1")
				.param("size", "12")
				.with(user(userDetails)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value(true))
			.andExpect(jsonPath("$.data.content", hasSize(1)))
			.andExpect(jsonPath("$.data.content[0].participationId").value(nullValue()))
			.andExpect(jsonPath("$.data.content[0].reviewId").value(nullValue()))
			.andExpect(jsonPath("$.data.content[0].title").value("REVIEW_TITLE"))
			.andExpect(jsonPath("$.data.content[0].createAt").value(nullValue()))
			.andExpect(jsonPath("$.data.content[0].submitAt").value(nullValue()))
			.andExpect(jsonPath("$.data.content[0].status").value("PROCEEDING"))
			.andDo(print());

		verify(participationService, times(1)).getAllParticipationsByResponserOfCursorPaging(
			cursorId,
			null,
			pageable);
	}
}
