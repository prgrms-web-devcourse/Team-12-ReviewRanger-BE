package com.devcourse.ReviewRanger.finalReviewResult.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.devcourse.ReviewRanger.finalReviewResult.domain.FinalReviewResult;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "최종 리뷰 생성 요청 DTO")
public record CreateFinalReviewRequest(

	@Schema(name = "수신자 id")
	@NotNull(message = "리뷰 대상 id는 빈값 일 수 없습니다.")
	Long userId,

	@Schema(name = "수신자 이름")
	@NotBlank(message = "리뷰 대상 이름은 빈값 일 수 없습니다.")
	String userName,

	@Schema(name = "리뷰 id")
	@NotNull(message = "리뷰 id는 빈값 일 수 없습니다.")
	Long reviewId,

	@Schema(name = "리뷰 제목")
	@NotBlank(message = "리뷰 제목은 빈값 일 수 없습니다.")
	String reviewTitle,

	@Schema(name = "리뷰 설명")
	String reviewDescription,

	@Schema(name = "해당 리뷰의 질문과 답변 리스트")
	@NotNull(message = "답변은 빈 값이 일 수 없습니다.")
	List<CreateFinalReplyRequest> replies
) {
	public FinalReviewResult toEntity() {
		return new FinalReviewResult(
			userId,
			userName,
			reviewId,
			reviewTitle,
			reviewDescription
		);
	}
}
