package com.devcourse.ReviewRanger.response.domain;

import com.devcourse.ReviewRanger.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "responses")
public class Response extends BaseEntity {

	@Column(name = "responser_id", nullable = false)
	private Long responserId;

	@Column(name = "each_survey_result_id", nullable = false)
	private Long eachSurveyResultId;

	@Column(name = "question_id", nullable = false)
	private Long questionId;

	@Column(name = "option_id", nullable = true)
	private Long optionId;

	@Lob
	@Column(name = "answer_text", nullable = true)
	private String answerText;

	protected Response() {
	}

	public Response(Long responserId, Long eachSurveyResultId, Long questionId) {
		this.responserId = responserId;
		this.eachSurveyResultId = eachSurveyResultId;
		this.questionId = questionId;
	}

	public Response(Long responserId, Long eachSurveyResultId, Long questionId, String answerText) {
		this(responserId, eachSurveyResultId, questionId);
		this.answerText = answerText;
	}

	public Response(Long responserId, Long eachSurveyResultId, Long questionId, Long optionId) {
		this(responserId, eachSurveyResultId, questionId);
		this.optionId = optionId;
	}
}
