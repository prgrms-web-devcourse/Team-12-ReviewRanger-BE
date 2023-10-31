package com.devcourse.ReviewRanger.surveyresult.domain;

import com.devcourse.ReviewRanger.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "survey_results")
public class SurveyResult extends BaseEntity {

	@Column(nullable = false)
	private Long surveyId;

	@Column(nullable = false)
	private Long responserId;

	@Column(nullable = false)
	private DeadlineStatus deadlineStatus;

	@Column(nullable = false)
	private boolean questionAnsweredStatus;

	protected SurveyResult() {
	}

	public SurveyResult(Long surveyId, Long responserId) {
		this.surveyId = surveyId;
		this.responserId = responserId;
		this.deadlineStatus = DeadlineStatus.PROCEEDING;
		this.questionAnsweredStatus = false;
	}
}
