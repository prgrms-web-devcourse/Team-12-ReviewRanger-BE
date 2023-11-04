package com.devcourse.ReviewRanger.question.application;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.security.core.parameters.P;
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
	public void createAllQuestions(List<Question> questions) {
		questions.forEach(question -> createQuestion(question));
	}

	@Transactional
	public void createQuestion(Question question) {
		Question createdQuestion = questionRepository.save(question);

		List<QuestionOption> questionOptions = createdQuestion.getQuestionOptions();
		questionOptions.forEach(questionOption -> questionOption.setQuestion(createdQuestion));
		createAllQuestionOptions(questionOptions);
	}

	@Transactional
	public void createAllQuestionOptions(List<QuestionOption> questionOptions) {
		questionOptionRepository.saveAll(questionOptions);
	}

	public Question getQuestion(Long questionId) {
		return questionRepository.findById(questionId).orElseThrow(EntityNotFoundException::new);
	}
}
