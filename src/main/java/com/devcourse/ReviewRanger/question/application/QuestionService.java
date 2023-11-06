package com.devcourse.ReviewRanger.question.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.question.domain.Question;
import com.devcourse.ReviewRanger.question.domain.QuestionOption;
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
	public void createQuestionInReview(Long reviewId, List<Question> questions) {
		questions.forEach(question -> question.assignReviewId(reviewId));
		List<Question> createdQuestion = questionRepository.saveAll(questions);

		for (Question question : createdQuestion) {
			if (question.getIsDuplicated()) {
				List<QuestionOption> questionOptions = question.getQuestionOptions();
				createQuestionOptionsInQuestion(question, questionOptions);
			}
		}
	}

	@Transactional
	public void createQuestionOptionsInQuestion(Question question,
		List<QuestionOption> questionOptions) {
		questionOptions.forEach(questionOption -> questionOption.setQuestion(question));
		List<QuestionOption> createdQuestionOptions = questionOptionRepository.saveAll(questionOptions);
	}
}
