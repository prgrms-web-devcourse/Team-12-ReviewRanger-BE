package com.devcourse.ReviewRanger.review.dto.response;

import java.time.LocalDateTime;

import com.devcourse.ReviewRanger.participation.domain.ReviewStatus;
import com.devcourse.ReviewRanger.review.domain.Review;
import com.devcourse.ReviewRanger.review.domain.ReviewType;
import com.devcourse.ReviewRanger.user.dto.UserResponse;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "리뷰 기본 응답 DTO")
public record ReviewResponse(
	@Schema(description = "리뷰 Id")
	Long id,

	@Schema(description = "생성자")
	UserResponse user, //creator

	@Schema(description = "리뷰 제목")
	String title,

	@Schema(description = "리뷰 내용")
	String description,

	@Schema(description = "리뷰 타입")
	ReviewType type,

	@Schema(description = "리뷰 마감일")
	LocalDateTime closedAt,

	@Schema(description = "리뷰 상태")
	ReviewStatus reviewStatus,

	@Schema(description = "리뷰 생성일")
	LocalDateTime createdAt,

	@Schema(description = "리뷰 수정일")
	LocalDateTime updatedAt
) {
	public static ReviewResponse toResponse(Review review, UserResponse user) {
		return new ReviewResponse(
			review.getId(),
			user,
			review.getTitle(),
			review.getDescription(),
			review.getType(),
			review.getClosedAt(),
			review.getStatus(),
			review.getCreateAt(),
			review.getUpdatedAt()
		);
	}
}
