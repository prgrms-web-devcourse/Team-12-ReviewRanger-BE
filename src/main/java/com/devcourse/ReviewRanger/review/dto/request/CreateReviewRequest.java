package com.devcourse.ReviewRanger.review.dto.request;

import java.util.List;

import com.devcourse.ReviewRanger.question.dto.request.CreateQuestionRequest;
import com.devcourse.ReviewRanger.review.domain.Review;
import com.devcourse.ReviewRanger.review.domain.ReviewType;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "리뷰 생성 및 요청 DTO")
public record CreateReviewRequest(
	@Schema(description = "리뷰 제목")
	String title,

	@Schema(description = "리뷰 내용")
	String description,

	@Schema(description = "리뷰 타입")
	ReviewType type,

	@Schema(description = "질문 목록")
	@JsonProperty("questions")
	List<CreateQuestionRequest> creatQuestionRequests,

	@Schema(description = "요청할 응답자 목록")
	@JsonProperty("responserIdList")
	List<Long> responserIds
) {

	public Review toEntity() {
		return new Review(this.title, this.description, this.type);
	}

}
