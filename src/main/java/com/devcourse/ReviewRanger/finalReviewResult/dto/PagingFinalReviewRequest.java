package com.devcourse.ReviewRanger.finalReviewResult.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "리뷰 결과 목록 페이징 요청 DTO")
public record PagingFinalReviewRequest(
	@Schema(description = "페이징 커서 id")
	@NotNull(message = "커서 id는 빈 값이 될 수 없습니다.")
	Long cursorId,

	@Schema(description = "페이지 반환 데이터 갯수")
	@NotNull(message = "size는 빈 값이 될 수 없습니다.")
	@Positive(message = "size는 양수 값 입니다.")
	Integer size
) {
}
