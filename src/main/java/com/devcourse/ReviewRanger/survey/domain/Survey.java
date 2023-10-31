package com.devcourse.ReviewRanger.survey.domain;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.*;

import java.time.LocalDateTime;

import com.devcourse.ReviewRanger.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Entity
@Table(name = "surveys")
public class Survey extends BaseEntity {

	@Column(name = "requester_id", nullable = false)
	Long requesterId;

	@Column(nullable = false)
	@NotBlank(message = "설문제목은 빈값 일 수 없습니다.")
	String title;

	String description;

	@Column(nullable = false)
	SurveyType type;

	@Column(name = "closed_at", nullable = false)
	@JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
	LocalDateTime closedAt;

	protected Survey() {
	}

	public Survey(String title, String description, SurveyType type) {
		this.title = title;
		this.description = description;
		this.type = type;
	}

	public void assignSurveyId(Long requesterId) {
		this.requesterId = requesterId;
	}
}
