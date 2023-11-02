package com.devcourse.ReviewRanger.surveyresult.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.devcourse.ReviewRanger.BaseEntity;

import lombok.Getter;

@Getter
@Entity
@Table(name = "survey_results")
public class SurveyResult extends BaseEntity {

	@Column(name = "survey_id", nullable = false)
	private Long surveyId;

	@Column(name = "responser_id", nullable = false)
	private Long responserId;

	@Column(name = "deadline_status", nullable = false)
	@Enumerated(EnumType.STRING)
	private DeadlineStatus deadlineStatus;

	@Column(name = "question_answered_status", nullable = false)
	private Boolean questionAnsweredStatus = false;

	protected SurveyResult() {
	}

	public SurveyResult(Long responserId) {
		this.responserId = responserId;
		this.deadlineStatus = DeadlineStatus.PROCEEDING;
		this.questionAnsweredStatus = false;
	}

	public void assignSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}

	public void changeStatus(DeadlineStatus status) {
		this.deadlineStatus = status;
	}
}
