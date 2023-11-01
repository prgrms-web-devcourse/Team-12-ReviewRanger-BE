package com.devcourse.ReviewRanger.question.application;

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
		questions.forEach(question -> question.assignSurveyId(surveyId));
		List<Question> createdQuestions = questionRepository.saveAll(questions);

		for (Question question : questions) {
			question.assignSurveyId(surveyId);
			Question createdQuestion = questionRepository.save(question);

			if (question.isAnswerDuplicated()) {
				createQuestionOptionsInQuestion(createdQuestion.getId(), question.createQuestionOptions());
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
