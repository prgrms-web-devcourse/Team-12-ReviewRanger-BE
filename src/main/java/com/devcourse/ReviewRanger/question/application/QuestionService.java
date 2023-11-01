package com.devcourse.ReviewRanger.question.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.ReviewRanger.question.domain.Question;
import com.devcourse.ReviewRanger.question.domain.QuestionOption;
import com.devcourse.ReviewRanger.question.repository.QuestionOptionRepository;
import com.devcourse.ReviewRanger.question.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

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
	public List<Question> createQuestionInSurvey(Long surveyId, List<Question> questions) {
		List<Question> createdQuestions = new ArrayList<>();

		for (Question question : questions) {
			question.assignSurveyId(surveyId);
			Question createdQuestion = questionRepository.save(question);
			createdQuestions.add(createdQuestion);

			if (question.getIsDuplicated()) {
				Long questionId = createdQuestion.getId();
				List<QuestionOption> questionOptions = question.createQuestionOptions();
				createQuestionOptionsInQuestion(questionId, questionOptions);
			}
		}

		return createdQuestions;
	}

	@Transactional
	public List<QuestionOption> createQuestionOptionsInQuestion(Long questionId, List<QuestionOption> questionOptions) {
		questionOptions.forEach(question -> question.assignedQuestionId(questionId));
		List<QuestionOption> createdQuestionOptions = questionOptionRepository.saveAll(questionOptions);

		return createdQuestionOptions;
	}
}
