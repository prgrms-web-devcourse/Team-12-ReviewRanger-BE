package com.devcourse.ReviewRanger.review.dto.response;

import java.time.LocalDateTime;

import com.devcourse.ReviewRanger.review.domain.Review;
import com.devcourse.ReviewRanger.review.domain.ReviewType;

import io.swagger.v3.oas.annotations.media.Schema;

public record ReviewResponseDto(
	@Schema(description = "리뷰 Id")
	Long reviewId,

	@Schema(description = "리뷰 제목")
	String title,

	@Schema(description = "리뷰 내용")
	String description,

	@Schema(description = "리뷰 타입")
	ReviewType reviewType,

	@Schema(description = "리뷰 마감일")
	LocalDateTime closeAt,

	@Schema(description = "리뷰 생성일")
	LocalDateTime createdAt,

	@Schema(description = "리뷰 수정일")
	LocalDateTime updatedAt
) {
	public ReviewResponseDto(
		Review review) {
		this(
			review.getId(),
			review.getTitle(),
			null,
			null,
			null,
			null,
			null
		);
	}
}
