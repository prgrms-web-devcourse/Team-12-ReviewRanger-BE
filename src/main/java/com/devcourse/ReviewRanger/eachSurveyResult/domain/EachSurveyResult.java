package com.devcourse.ReviewRanger.eachSurveyResult.domain;

import com.devcourse.ReviewRanger.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "each_survey_results")
public class EachSurveyResult extends BaseEntity {

	@Column(name = "subject_id", nullable = false)
	private Long subjectId;

	@Column(name = "survey_result_id", nullable = false)
	private Long surveyResultId;

	protected EachSurveyResult() {
	}

	public EachSurveyResult(Long subjectId, Long surveyResultId) {
		this.subjectId = subjectId;
		this.surveyResultId = surveyResultId;
	}
}
