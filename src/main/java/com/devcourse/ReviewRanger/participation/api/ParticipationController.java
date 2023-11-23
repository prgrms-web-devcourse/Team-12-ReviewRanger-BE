package com.devcourse.ReviewRanger.participation.api;

import static org.springframework.http.HttpStatus.*;

import javax.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.devcourse.ReviewRanger.auth.domain.UserPrincipal;
import com.devcourse.ReviewRanger.common.response.RangerResponse;
import com.devcourse.ReviewRanger.participation.application.ParticipationService;
import com.devcourse.ReviewRanger.participation.dto.request.SubmitParticipationRequest;
import com.devcourse.ReviewRanger.participation.dto.request.UpdateParticipationRequest;
import com.devcourse.ReviewRanger.participation.dto.response.GetParticipationResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "participation", description = "참여 API")
public class ParticipationController {

	private final ParticipationService participationService;

	public ParticipationController(ParticipationService participationService) {
		this.participationService = participationService;
	}

	@Tag(name = "participation")
	@Operation(summary = "[토큰] 응답자의 초대받은 리뷰 전체조회", description = "[토큰] 응답자의 초대받은 리뷰 전체조회 API", responses = {
		@ApiResponse(responseCode = "200", description = "조회 성공")
	})
	@GetMapping("/participation")
	public RangerResponse<Slice<GetParticipationResponse>> getAllParticipationsByResponserOfCursorPaging(
		@AuthenticationPrincipal UserPrincipal user,
		@RequestParam(required = false) Long cursorId,
		@RequestParam(defaultValue = "12") Integer size
	) {
		Pageable pageable = PageRequest.of(0, size);
		Slice<GetParticipationResponse> responses = participationService.getAllParticipationsByResponserOfCursorPaging(
			cursorId,
			user.getId(),
			pageable
		);

		return RangerResponse.ok(responses);
	}

	@Tag(name = "participation")
	@Operation(summary = "[토큰] 리뷰 답변 기능", description = "[토큰] 리뷰 답변 API", responses = {
		@ApiResponse(responseCode = "201", description = "리뷰 답변 성공"),
		@ApiResponse(responseCode = "404", description = "사용자가 존재하지 않는 경우"),
		@ApiResponse(responseCode = "400", description = "필수 질문에 대한 답변이 없는 경우"),
	})
	@ResponseStatus(CREATED)
	@PostMapping(value = "/participation")
	public RangerResponse<Void> submitParticipation(@RequestBody @Valid SubmitParticipationRequest request) {
		participationService.submitReplies(request);

		return RangerResponse.noData();
	}

	@Tag(name = "participation")
	@Operation(summary = "[토큰] 리뷰 답변 수정 기능", description = "[토큰] 리뷰 답변 수정 API", responses = {
		@ApiResponse(responseCode = "200", description = "리뷰 답변 수정 성공"),
		@ApiResponse(responseCode = "404", description = "답변이 존재하지 않는 경우"),
		@ApiResponse(responseCode = "400", description = "필수 질문에 대한 답변이 없는 경우")
	})
	@PutMapping(value = "/participation")
	public RangerResponse<Void> updateParticipation(@RequestBody @Valid UpdateParticipationRequest request) {
		participationService.updateResponse(request);

		return RangerResponse.noData();
	}
}
