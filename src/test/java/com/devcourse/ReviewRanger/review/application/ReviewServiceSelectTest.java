package com.devcourse.ReviewRanger.review.application;

import static com.devcourse.ReviewRanger.common.exception.ErrorCode.*;
import static com.devcourse.ReviewRanger.participation.domain.ReviewStatus.*;
import static com.devcourse.ReviewRanger.review.ReviewFixture.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import com.devcourse.ReviewRanger.common.exception.RangerException;
import com.devcourse.ReviewRanger.participation.application.ParticipationService;
import com.devcourse.ReviewRanger.participation.domain.ReviewStatus;
import com.devcourse.ReviewRanger.question.application.QuestionService;
import com.devcourse.ReviewRanger.review.domain.Review;
import com.devcourse.ReviewRanger.review.dto.response.GetReviewDetailResponse;
import com.devcourse.ReviewRanger.review.dto.response.GetReviewResponse;
import com.devcourse.ReviewRanger.review.repository.ReviewRepository;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceSelectTest {

	@InjectMocks
	private ReviewService reviewService;

	@Mock
	private ReviewRepository reviewRepository;

	@Mock
	private QuestionService questionService;

	@Mock
	private ParticipationService participationService;

	@Test
	void 리뷰_상세조회_성공() {
		// given
		Review fakeReview = BASIC_REVIEW.toEntity();

		Long reviewId =1L;
		Long responserId =1L;
		when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(fakeReview));
		when(questionService.getAllQuestionsByReview(reviewId)).thenReturn(List.of());
		// when
		GetReviewDetailResponse getReviewDetailResponse = reviewService.getReviewDetailOrThrow(reviewId,responserId);

		// then
		verify(reviewRepository, times(1)).findById(reviewId);
		verify(questionService, times(1)).getAllQuestionsByReview(reviewId);

		assertEquals("예시 리뷰", getReviewDetailResponse.title());
		assertEquals("예시 리뷰 설명입니다.", getReviewDetailResponse.description());
		assertEquals(PROCEEDING, getReviewDetailResponse.status());
		assertEquals(List.of(), getReviewDetailResponse.questions());
	}

	@Test
	void 리뷰_상세조회_실패() {
		// given
		Review fakeReview = BASIC_REVIEW.toEntity();
		Long wrongReviewId =1L;
		Long responserId =1L;

		// when
		// then
		Assertions.assertThatThrownBy(() -> reviewService.getReviewDetailOrThrow(wrongReviewId, responserId))
			.isInstanceOf(RangerException.class)
			.hasMessage(NOT_FOUND_REVIEW.getMessage());
	}

	@Test
	void 리뷰_전체조회_성공(){
		// given
		Long cursorId = 1L;
		Long requesterId = 1L;
		Pageable pageable = PageRequest.of(0,12);

		List<Review> reviews = List.of(BASIC_REVIEW.toEntity(),BASIC_REVIEW.toEntity());
		boolean hasNext = true;
		SliceImpl<Review> reviewsResult = new SliceImpl<>(reviews, pageable, hasNext);
		Long responserCount = 5L;

		when(reviewRepository.findByRequesterId(cursorId,requesterId, pageable)).thenReturn(reviewsResult);
		when(participationService.getResponserCount(null)).thenReturn(responserCount);

		// when
		Slice<GetReviewResponse> getReviewResponse = reviewService.getAllReviewsByRequesterOfCursorPaging(
			cursorId, requesterId, pageable);

		// then
		verify(reviewRepository,times(1)).findByRequesterId(cursorId,requesterId, pageable);
		verify(participationService,times(2)).getResponserCount(null);

		assertEquals(5, getReviewResponse.getContent().get(0).responserCount());
		assertEquals("예시 리뷰", getReviewResponse.getContent().get(0).title());
		assertEquals(PROCEEDING, getReviewResponse.getContent().get(0).status());

		assertEquals(5, getReviewResponse.getContent().get(1).responserCount());
		assertEquals("예시 리뷰", getReviewResponse.getContent().get(1).title());
		assertEquals(PROCEEDING, getReviewResponse.getContent().get(1).status());
	}

	@Test
	void 리뷰_마감_성공(){
		// gien
		Long reviewId = 1L;
		Review mockReview = BASIC_REVIEW.toEntity();

		when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(mockReview));
		doNothing().when(participationService).closeParticipationOrThrow(reviewId);

		// when
		reviewService.closeReviewOrThrow(reviewId);

		// then
		assertEquals(DEADLINE, mockReview.getStatus());
		verify(reviewRepository, times(1)).findById(reviewId);
		verify(participationService, times(1)).closeParticipationOrThrow(reviewId);
	}
}
