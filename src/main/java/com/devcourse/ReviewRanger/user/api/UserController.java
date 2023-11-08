package com.devcourse.ReviewRanger.user.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.devcourse.ReviewRanger.common.response.RangerResponse;
import com.devcourse.ReviewRanger.user.application.UserService;
import com.devcourse.ReviewRanger.user.domain.User;
import com.devcourse.ReviewRanger.user.domain.UserPrincipal;
import com.devcourse.ReviewRanger.user.dto.GetUserResponse;
import com.devcourse.ReviewRanger.user.dto.JoinRequest;
import com.devcourse.ReviewRanger.user.dto.LoginRequest;
import com.devcourse.ReviewRanger.user.dto.LoginResponse;
import com.devcourse.ReviewRanger.user.dto.UpdateRequest;
import com.devcourse.ReviewRanger.user.dto.ValidateEmailRequest;
import com.devcourse.ReviewRanger.user.dto.ValidateNameRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "user", description = "사용자 API")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@Tag(name = "user")
	@Operation(summary = "회원가입", description = "사용자가 회원가입을 요청하는 API", responses = {
		@ApiResponse(responseCode = "200", description = "회원가입 성공"),
		@ApiResponse(responseCode = "422", description = "잘못된 로그인 정보를 입력한 경우"),
	})
	@PostMapping("/sign-up")
	public RangerResponse<Void> join(@RequestBody @Valid JoinRequest request) {
		Boolean joinSuccess = userService.join(request);

		return RangerResponse.ok(joinSuccess);
	}

	@Tag(name = "user")
	@PostMapping("/login")
	public RangerResponse<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
		LoginResponse loginResponse = userService.login(request);

		return RangerResponse.ok(loginResponse);
	}

	@Tag(name = "user")
	@PostMapping("/members/logout")
	public RangerResponse<Void> logout(
		@RequestHeader("Authorization") String accessToken
	) {
		userService.logout(accessToken);
		return RangerResponse.noData();
	}

	@Tag(name = "user")
	@PostMapping("/members/check-name")
	public RangerResponse<Void> checkname(@RequestBody @Valid ValidateNameRequest request) {
		boolean notExistName = userService.isNotExistName(request.name());

		return RangerResponse.ok(notExistName);
	}

	@Tag(name = "user")
	@PostMapping("/members/check-email")
	public RangerResponse<Void> checkEmail(@RequestBody @Valid ValidateEmailRequest request) {
		boolean notExistEmail = userService.isNotExistEmail(request.email());

		return RangerResponse.ok(notExistEmail);
	}

	@Tag(name = "user")
	@PatchMapping("/members/profile")
	public RangerResponse<Void> update(
		@AuthenticationPrincipal UserPrincipal user,
		@RequestBody @Valid UpdateRequest updateRequest
	) {
		userService.updateInfo(user.getId(), updateRequest);
		return RangerResponse.noData();
	}

	@Tag(name = "user")
	@GetMapping("/members")
	public RangerResponse<List<GetUserResponse>> getAllUsers() {
		List<GetUserResponse> allUsers = userService.getAllUsers();

		return RangerResponse.ok(allUsers);
	}
}
