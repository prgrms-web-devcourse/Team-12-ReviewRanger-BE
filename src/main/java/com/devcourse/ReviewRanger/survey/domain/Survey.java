package com.devcourse.ReviewRanger.survey.domain;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.*;

import java.time.LocalDateTime;

import com.devcourse.ReviewRanger.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@Entity
@Table(name = "surveys")
public class Survey extends BaseEntity {

	@Column(name = "requester_id", nullable = false)
	@NotBlank(message = "요청자 Id는 빈값 일 수 없습니다.")
	Long requesterId;

	@Column(nullable = false, length = 50)
	@NotBlank(message = "설문제목은 빈값 일 수 없습니다.")
	@Size(max = 50, message = "50자 이하로 입력하세요.")
	String title;

	@Column(length = 100)
	@Size(max = 100, message = "100자 이하로 입력하세요.")
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
