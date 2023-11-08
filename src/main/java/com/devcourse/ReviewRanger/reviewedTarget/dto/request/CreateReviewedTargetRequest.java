package com.devcourse.ReviewRanger.reviewedTarget.dto.request;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.devcourse.ReviewRanger.reply.dto.request.CreateReplyRequest;
import com.devcourse.ReviewRanger.reviewedTarget.domain.ReviewedTarget;

public record CreateReviewedTargetRequest(
	@NotBlank(message = "리뷰 대상 Id는 빈값 일 수 없습니다.")
	Long subjectId,

	List<CreateReplyRequest> responses
) {
	public ReviewedTarget toEntity() {
		return new ReviewedTarget(subjectId);
	}
}
