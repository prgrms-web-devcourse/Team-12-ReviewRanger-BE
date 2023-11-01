package com.devcourse.ReviewRanger.surveyresult.domain;

import com.devcourse.ReviewRanger.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
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
	private Boolean questionAnsweredStatus;

	protected SurveyResult() {
	}

	public SurveyResult(Long surveyId, Long responserId) {
		this.surveyId = surveyId;
		this.responserId = responserId;
		this.deadlineStatus = DeadlineStatus.PROCEEDING;
		this.questionAnsweredStatus = false;
	}
}
