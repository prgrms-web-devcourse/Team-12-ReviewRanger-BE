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

import com.devcourse.ReviewRanger.common.exception.RangerException;
import com.devcourse.ReviewRanger.question.application.QuestionService;
import com.devcourse.ReviewRanger.review.domain.Review;
import com.devcourse.ReviewRanger.review.dto.response.GetReviewDetailResponse;
import com.devcourse.ReviewRanger.review.repository.ReviewRepository;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceSelectTest {

	@InjectMocks
	private ReviewService reviewService;

	@Mock
	private ReviewRepository reviewRepository;

	@Mock
	private QuestionService questionService;

	@Test
	void 리뷰_상세조회_성공() {
		// given
		Review fakeReview = BASIC_REVIEW.toEntity();

		Long reviewId =1L;
		when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(fakeReview));
		when(questionService.getAllQuestionsByReview(reviewId)).thenReturn(List.of());

		// when
		GetReviewDetailResponse getReviewDetailResponse = reviewService.getReviewDetailOrThrow(reviewId);

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

		// when
		// then
		Assertions.assertThatThrownBy(() -> reviewService.getReviewDetailOrThrow(wrongReviewId))
			.isInstanceOf(RangerException.class)
			.hasMessage(NOT_FOUND_REVIEW.getMessage());
	}
}
