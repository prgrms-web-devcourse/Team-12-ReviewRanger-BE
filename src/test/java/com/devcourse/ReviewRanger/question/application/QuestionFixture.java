package com.devcourse.ReviewRanger.question.application;

import static com.devcourse.ReviewRanger.question.domain.QuestionType.*;

import java.util.List;

import com.devcourse.ReviewRanger.question.domain.Question;
import com.devcourse.ReviewRanger.question.domain.QuestionType;
import com.devcourse.ReviewRanger.question.dto.response.GetQuestionOptionResponse;
import com.devcourse.ReviewRanger.question.dto.response.GetQuestionResponse;

public enum QuestionFixture {
	BASIC_QUESTION("질문 제목", "질문 설명", SUBJECTIVE, true);

	private final String title;
	private final String description;
	private final QuestionType type;
	private final boolean isRequired;

	QuestionFixture(String title, String description, QuestionType type, boolean isRequired) {
		this.title = title;
		this.description = description;
		this.type = type;
		this.isRequired = isRequired;
	}

	public Question toEntity() {
		return new Question(title, description, type, isRequired);
	}

	public GetQuestionResponse toGetQuestionResponse() {
		Question question = new Question(title, description, type, isRequired);
		List<GetQuestionOptionResponse> getQuestionOptionResponse = List.of();
		return new GetQuestionResponse(question, getQuestionOptionResponse);
	}

}
