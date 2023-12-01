package com.devcourse.ReviewRanger.review.domain;

import static com.devcourse.ReviewRanger.participation.domain.ReviewStatus.*;
import static com.fasterxml.jackson.annotation.JsonFormat.Shape.*;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.devcourse.ReviewRanger.BaseEntity;
import com.devcourse.ReviewRanger.participation.domain.ReviewStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

@Getter
@Entity
@Table(name = "reviews")
public class Review extends BaseEntity {

	@Column(name = "requester_id", nullable = false)
	private Long requesterId;

	@Column(nullable = false, length = 50)
	@NotBlank(message = "설문제목은 빈값 일 수 없습니다.")
	@Size(max = 50, message = "50자 이하로 입력하세요.")
	private String title;

	@Column(length = 500)
	@Size(max = 500, message = "500자 이하로 입력하세요.")
	private String description;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ReviewType type;

	@Column(name = "closed_at", nullable = false)
	@JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime closedAt;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private ReviewStatus status;

	protected Review() {
	}

	public Review(String title, String description, ReviewType type) {
		this.title = title;
		this.description = description;
		this.type = type;
		this.closedAt = LocalDateTime.now().plus(Duration.ofDays(100));
		this.status = PROCEEDING;
	}

	public void assignRequesterId(Long requesterId) {
		this.requesterId = requesterId;
	}

	public void changeStatus(ReviewStatus status) {
		this.status = status;
	}

	public void toClose() {
		this.status = END;
		this.closedAt = LocalDateTime.now();
	}
}
