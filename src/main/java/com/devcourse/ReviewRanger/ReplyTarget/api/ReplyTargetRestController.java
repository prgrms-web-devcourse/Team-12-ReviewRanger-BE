package com.devcourse.ReviewRanger.ReplyTarget.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.devcourse.ReviewRanger.ReplyTarget.application.ReplyTargetService;
import com.devcourse.ReviewRanger.ReplyTarget.dto.response.ReplyTargetResponse;
import com.devcourse.ReviewRanger.common.response.RangerResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "reply-target", description = "리뷰 타겟 API")
@RestController
public class ReplyTargetRestController {

	private final ReplyTargetService replyTargetService;

	public ReplyTargetRestController(ReplyTargetService replyTargetService) {
		this.replyTargetService = replyTargetService;
	}

	@Tag(name = "reply-target")
	@Operation(summary = "응답자별 답변 조회 기능", description = "응답자별 답변 조회 API", responses = {
		@ApiResponse(responseCode = "200", description = "응답자별 답변 조회 성공"),
		@ApiResponse(responseCode = "404", description = "수신자가 존재하지 않는 경우"),

	})
	@GetMapping("/reviews/{reviewId}/responser/{responserId}")
	public RangerResponse<List<ReplyTargetResponse>> getRepliesByResponser(@PathVariable Long reviewId,
		@PathVariable Long responserId) {
		List<ReplyTargetResponse> responses = replyTargetService.getAllRepliesByResponser(
			reviewId, responserId);

		return RangerResponse.ok(responses);
	}

	@Tag(name = "reply-target")
	@Operation(summary = "수신자별 답변 조회 기능", description = "수신자별 답변 조회 API", responses = {
		@ApiResponse(responseCode = "200", description = "수신자별 답변 조회 성공"),
		@ApiResponse(responseCode = "404", description = "수신자가 존재하지 않는 경우"),

	})
	@GetMapping("/reviews/{reviewId}/receiver/{receiverId}")
	public RangerResponse<List<ReplyTargetResponse>> getRepliesByReceiver(@PathVariable Long reviewId,
		@PathVariable Long receiverId) {
		List<ReplyTargetResponse> responses = replyTargetService.getAllRepliesByReceiver(reviewId, receiverId);

		return RangerResponse.ok(responses);
	}

	@Tag(name = "reply-target")
	@Operation(summary = "테스트1", description = "테스트 API", responses = {
		@ApiResponse(responseCode = "200", description = "테스트 성공"),
	})
	@GetMapping("/reviews/{reviewId}/test")
	public RangerResponse<Void> getRepliesByReceiver(@PathVariable Long reviewId) {
		System.out.println(reviewId);

		return RangerResponse.noData();
	}
}
