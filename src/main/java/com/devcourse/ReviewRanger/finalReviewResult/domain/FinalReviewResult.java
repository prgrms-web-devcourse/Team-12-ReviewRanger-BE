package com.devcourse.ReviewRanger.finalReviewResult.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.devcourse.ReviewRanger.BaseEntity;

import lombok.Getter;

@Getter
@Entity
@Table(name = "final_review_results")
public class FinalReviewResult extends BaseEntity {

	@Column(name = "user_id", nullable = false)
	@NotBlank(message = "리뷰 대상 id는 빈값 일 수 없습니다.")
	private Long userId;

	@Column(name = "review_id", nullable = false)
	@NotBlank(message = "리뷰 id는 빈값 일 수 없습니다.")
	private Long reviewId;

	@Column(nullable = false, length = 50)
	@NotBlank(message = "리뷰 제목은 빈값 일 수 없습니다.")
	@Size(max = 50, message = "50자 이하로 입력하세요.")
	private String title;
}
