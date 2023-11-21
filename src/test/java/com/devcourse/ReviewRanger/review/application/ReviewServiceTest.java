package com.devcourse.ReviewRanger.review.application;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.devcourse.ReviewRanger.question.dto.request.CreateQuestionRequest;
import com.devcourse.ReviewRanger.review.domain.ReviewType;
import com.devcourse.ReviewRanger.review.dto.request.CreateReviewRequest;
import com.devcourse.ReviewRanger.review.repository.ReviewRepository;

@SpringBootTest
class ReviewServiceTest {

	@Autowired
	private ReviewService reviewService;

	@Test
	void 리뷰_생성_성공() {
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

		Long requesterId = 1L;

		// when
		boolean result = reviewService.createReview(requesterId, createReviewRequest);

		// then
		assertTrue(result);
	}

	@Test
	void 리뷰제목_빈값에_따른_리뷰_생성_실패() {
		// given
		String title = "";
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

		Long requesterId = 1L;

		// when then
		assertThrows(ConstraintViolationException.class, ()->{
			reviewService.createReview(requesterId, createReviewRequest);
		});
	}

	@Test
	void 리뷰제목_50자_이상에_따른_리뷰_생성_실패() {
		// given
		String title = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
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

		Long requesterId = 1L;

		// when then
		assertThrows(ConstraintViolationException.class, ()->{
			reviewService.createReview(requesterId, createReviewRequest);
		});
	}

	@Test
	void 리뷰설명_100자_이상에_따른_리뷰_생성_실패() {
		// given
		String title = "테스트 리뷰";
		String description = """
			aB3XzL7vYp0Kq1iFgT5uH2rW8cO4sD6nJ9mVxZlEoGyIuPqRtS3XbA2CvKjN0MfaB3XzL
			7vYp0Kq1iFgT5uH2rW8cO4sD6nJ9mVxZlEoGyIuPqRtS3XbA2CvKjN0MfaB3XzL7vYp0K
			1iFgT5uH2rW8cO4sD6nJ9mVxZlEoGyIuPqRtS3XbA2CvKjN0MfaB3XzL7vYp0Kq1iFgT5
			uH2rW8cO4sD6nJ9mVxZlEoGyIuPqRtS3XbA2CvKjN0MfaB3XzL7vYp0Kq1iFgT5uH2rW8
			cO4sD6nJ9mVxZlEoGyIuPqRtS3XbA2CvKjN0Mf
			""";
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

		Long requesterId = 1L;

		// when then
		assertThrows(ConstraintViolationException.class, ()->{
			reviewService.createReview(requesterId, createReviewRequest);
		});
	}

	@Test
	void 리뷰생성자_부재에_따른_리뷰_생성_실패() {
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

		Long requesterId = null;

		// when then
		assertThrows(DataIntegrityViolationException.class, ()->{
			reviewService.createReview(requesterId, createReviewRequest);
		});
	}
}