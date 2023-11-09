package com.devcourse.ReviewRanger.finalReviewResult.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.devcourse.ReviewRanger.BaseEntity;

import lombok.Getter;

@Getter
@Entity
@Table(name = "final_review_results")
public class FinalReviewResult extends BaseEntity {

	private enum Status {
		NOT_SENT,
		SENT;
	}

	@Column(name = "user_id", nullable = false)
	@NotBlank(message = "리뷰 대상 id는 빈값 일 수 없습니다.")
	private Long userId;

	@Column(name = "user_name", nullable = false)
	private String userName;

	@Column(name = "review_id", nullable = false)
	@NotBlank(message = "리뷰 id는 빈값 일 수 없습니다.")
	private Long reviewId;

	@Column(nullable = false, length = 50)
	@NotBlank(message = "리뷰 제목은 빈값 일 수 없습니다.")
	@Size(max = 50, message = "50자 이하로 입력하세요.")
	private String title;

	@Lob
	@Column(nullable = true)
	private String description;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status;

	// @ElementCollection
	// @CollectionTable(name = "final_review_replies")
	// @OrderColumn(name = "reply_index")
	@OneToMany(mappedBy = "finalReviewResult")
	private List<FinalReviewResultReply> finalReviewResultReply = new ArrayList<>();

	protected FinalReviewResult() {
	}

	public FinalReviewResult(Long userId, String userName, Long reviewId, String title, String description) {
		this.userId = userId;
		this.userName = userName;
		this.reviewId = reviewId;
		this.title = title;
		this.description = description;
		this.status = Status.NOT_SENT;
	}

	public void toSentStatus() {
		this.status = Status.SENT;
	}
}
