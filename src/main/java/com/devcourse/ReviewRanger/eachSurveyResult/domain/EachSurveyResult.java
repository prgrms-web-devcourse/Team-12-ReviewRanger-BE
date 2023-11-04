package com.devcourse.ReviewRanger.eachSurveyResult.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.devcourse.ReviewRanger.BaseEntity;
import com.devcourse.ReviewRanger.survey.domain.Survey;
import com.devcourse.ReviewRanger.surveyresult.domain.SurveyResult;

import lombok.Getter;

@Getter
@Entity
@Table(name = "each_survey_results")
public class EachSurveyResult extends BaseEntity {

	@Column(name = "subject_id", nullable = false)
	private Long subjectId;

	@ManyToOne
	@JoinColumn(name = "survey_result_id")
	private SurveyResult surveyResult;

	protected EachSurveyResult() {
	}

	public EachSurveyResult(Long subjectId){
		this.subjectId = subjectId;
	}

	public void assignSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public void setSurveyResult(SurveyResult surveyResult) {
		this.surveyResult = surveyResult;
	}

}
