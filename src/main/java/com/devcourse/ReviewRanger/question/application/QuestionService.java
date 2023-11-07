package com.devcourse.ReviewRanger.question.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.question.domain.Question;
import com.devcourse.ReviewRanger.question.domain.QuestionOption;
import com.devcourse.ReviewRanger.question.dto.request.CreateQuestionOptionRequest;
import com.devcourse.ReviewRanger.question.dto.request.CreateQuestionRequest;
import com.devcourse.ReviewRanger.question.repository.QuestionOptionRepository;
import com.devcourse.ReviewRanger.question.repository.QuestionRepository;

@Service
@Transactional(readOnly = true)
public class QuestionService {

	private final QuestionRepository questionRepository;
	private final QuestionOptionRepository questionOptionRepository;

	public QuestionService(QuestionRepository questionRepository, QuestionOptionRepository questionOptionRepository) {
		this.questionRepository = questionRepository;
		this.questionOptionRepository = questionOptionRepository;
	}

	@Transactional
	public Boolean createQuestions(Long reviewId, List<CreateQuestionRequest> createQuestionRequests) {
		for (CreateQuestionRequest createQuestionRequest : createQuestionRequests) {
			Question question = createQuestionRequest.toEntity();
			question.assignReviewId(reviewId);
			Question savedQuestion = questionRepository.save(question);

			createQuestionOptions(savedQuestion, createQuestionRequest.createQuestionOptionRequests());
		}

		return true;
	}

	@Transactional
	public Boolean createQuestionOptions(
		Question question,
		List<CreateQuestionOptionRequest> createQuestionOptionRequests
	) {
		for (CreateQuestionOptionRequest createQuestionOptionRequest : createQuestionOptionRequests) {
			QuestionOption questionOption = createQuestionOptionRequest.toEntity();
			questionOption.assignQuestion(question);

			questionOptionRepository.save(questionOption);
		}

		return true;
	}
}
