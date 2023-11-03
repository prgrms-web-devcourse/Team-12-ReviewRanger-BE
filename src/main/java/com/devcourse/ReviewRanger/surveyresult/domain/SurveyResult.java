package com.devcourse.ReviewRanger.surveyresult.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.devcourse.ReviewRanger.BaseEntity;
import com.devcourse.ReviewRanger.common.constant.Status;
import com.devcourse.ReviewRanger.survey.domain.Survey;

import lombok.Getter;

@Getter
@Entity
@Table(name = "survey_results")
public class SurveyResult extends BaseEntity {

	@Column(name = "responser_id", nullable = false)
	private Long responserId;

	@Column(name = "deadline_status", nullable = false)
	@Enumerated(EnumType.STRING)
	private Status deadlineStatus;

	@Column(name = "question_answered_status", nullable = false)
	private Boolean questionAnsweredStatus = false;

	@ManyToOne
	@JoinColumn(name = "survey_id")
	private Survey survey;

	protected SurveyResult() {
	}

	public SurveyResult(Long responserId) {
		this.responserId = responserId;
		this.deadlineStatus = Status.PROCEEDING;
		this.questionAnsweredStatus = false;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public void changeStatus(Status status) {
		this.deadlineStatus = status;
	}
}
