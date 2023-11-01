package com.devcourse.ReviewRanger.question.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devcourse.ReviewRanger.question.domain.QuestionOption;
import com.devcourse.ReviewRanger.question.repository.QuestionOptionRepository;
import com.devcourse.ReviewRanger.question.repository.QuestionRepository;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

	@InjectMocks
	private QuestionService questionService;

	@Mock
	private QuestionRepository questionRepository;

	@Mock
	private QuestionOptionRepository questionOptionRepository;

	@Test
	public void 옵션이_있는_설문의_질문_생성_성공() {
		// given
		Long questionId = 1L;
		List<QuestionOption> questionOptions = List.of(
			new QuestionOption(
				"optionContext"
			),
			new QuestionOption(
				"optionContext2"
			)
		);

		when(questionOptionRepository.saveAll(questionOptions)).thenReturn(questionOptions);

		// when
		List<QuestionOption> createdQuestionOptions = questionService.createQuestionOptionsInQuestion(questionId,
			questionOptions);

		// then
		verify(questionOptionRepository).saveAll(questionOptions);
		assertEquals(questionOptions, createdQuestionOptions);
	}

}