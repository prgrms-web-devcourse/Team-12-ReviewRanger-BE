package com.devcourse.ReviewRanger.response.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.devcourse.ReviewRanger.BaseEntity;
import com.devcourse.ReviewRanger.eachSurveyResult.domain.EachSurveyResult;
import com.devcourse.ReviewRanger.question.domain.Question;
import com.devcourse.ReviewRanger.surveyresult.domain.SurveyResult;

import lombok.Getter;

@Getter
@Entity
@Table(name = "responses")
public class Response extends BaseEntity {

	@Column(name = "responser_id", nullable = false)
	private Long responserId;
	
	@Column(name = "option_id", nullable = true)
	private Long optionId;

	@Lob
	@Column(name = "answer_text", nullable = true)
	private String answerText;

	@ManyToOne
	@JoinColumn(name = "question_id")
	private Question question;

	@ManyToOne
	@JoinColumn(name = "each_survey_result_id")
	private EachSurveyResult eachSurveyResult;

	protected Response() {
	}

	public Response(Long responserId, String answerText) {
		this.responserId = responserId;
		this.answerText = answerText;
	}

	public void assignresponserId(Long responserId) {
		this.responserId = responserId;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public void setEachSurveyResult(EachSurveyResult eachSurveyResult) {
		this.eachSurveyResult = eachSurveyResult;
	}

}
