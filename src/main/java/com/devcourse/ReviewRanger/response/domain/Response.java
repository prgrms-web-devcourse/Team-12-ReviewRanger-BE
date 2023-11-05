package com.devcourse.ReviewRanger.response.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.devcourse.ReviewRanger.BaseEntity;
import com.devcourse.ReviewRanger.eachSurveyResult.domain.EachSurveyResult;

import lombok.Getter;

@Getter
@Entity
@Table(name = "responses")
public class Response extends BaseEntity {

	@Column(name = "responser_id", nullable = false)
	private Long responserId;

	@Column(name = "question_id", nullable = false)
	private Long questionId;

	@Lob
	@Column(name = "answer_text", nullable = true)
	private String answerText;

	@ManyToOne
	@JoinColumn(name = "each_survey_result_id")
	private EachSurveyResult eachSurveyResult;

	protected Response() {
	}

	public Response(Long responserId, EachSurveyResult eachSurveyResult, Long questionId) {
		this.responserId = responserId;
		this.eachSurveyResult = eachSurveyResult;
		this.questionId = questionId;
	}

	public Response(Long responserId, EachSurveyResult eachSurveyResult, Long questionId, String answerText) {
		this(responserId, eachSurveyResult, questionId);
		this.answerText = answerText;
	}
}
