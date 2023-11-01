package com.devcourse.ReviewRanger.afterresult.domain;

import com.devcourse.ReviewRanger.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "after_results")
public class AfterResult extends BaseEntity {

	@Column(name = "user_id", nullable = false)
	@NotBlank(message = "리뷰 대상 id는 빈값 일 수 없습니다.")
	private Long userId;

	@Column(name = "survey_id", nullable = false)
	@NotBlank(message = "설문 id는 빈값 일 수 없습니다.")
	private Long surveyId;

	@Column(nullable = false, length = 50)
	@NotBlank(message = "설문제목은 빈값 일 수 없습니다.")
	@Size(max = 50, message = "50자 이하로 입력하세요.")
	private String title;
}
