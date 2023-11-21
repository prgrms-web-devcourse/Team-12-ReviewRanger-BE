package com.devcourse.ReviewRanger.question.application;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.devcourse.ReviewRanger.question.domain.QuestionType;
import com.devcourse.ReviewRanger.question.dto.request.CreateQuestionOptionRequest;
import com.devcourse.ReviewRanger.question.dto.request.CreateQuestionRequest;

@SpringBootTest
class QuestionServiceTest {

	@Autowired
	QuestionService questionService;

	@Test
	void 질문_생성_성공() {
		// given
		String title = "질문 내용입니다.";
		String description = "질문 설명입니다.";
		QuestionType type = QuestionType.SUBJECTIVE;
		Boolean isRequired = true;
		List<CreateQuestionOptionRequest> questionOptions = List.of();

		CreateQuestionRequest createQuestionRequest = new CreateQuestionRequest(
			title,
			description,
			type,
			isRequired,
			questionOptions
			);

		Long reviewId = 1L;

		// when
		Boolean result = questionService.createQuestions(reviewId, List.of(createQuestionRequest));

		// then
		assertTrue(result);
	}

	@Test
	void 질문제목_빈값에_따른_질문_생성_실패() {
		// given
		String title = "";
		String description = "질문 설명입니다.";
		QuestionType type = QuestionType.SUBJECTIVE;
		Boolean isRequired = true;
		List<CreateQuestionOptionRequest> questionOptions = List.of();

		CreateQuestionRequest createQuestionRequest = new CreateQuestionRequest(
			title,
			description,
			type,
			isRequired,
			questionOptions
		);

		Long reviewId = 1L;

		// when then
		assertThrows(ConstraintViolationException.class, ()->{
			questionService.createQuestions(reviewId, List.of(createQuestionRequest));
		});
	}
}