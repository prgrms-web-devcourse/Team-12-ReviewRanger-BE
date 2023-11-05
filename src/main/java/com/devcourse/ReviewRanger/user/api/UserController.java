package com.devcourse.ReviewRanger.user.api;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.devcourse.ReviewRanger.common.response.RangerResponse;
import com.devcourse.ReviewRanger.user.application.UserService;
import com.devcourse.ReviewRanger.user.domain.UserPrincipal;
import com.devcourse.ReviewRanger.user.dto.JoinRequest;
import com.devcourse.ReviewRanger.user.dto.LoginRequest;
import com.devcourse.ReviewRanger.user.dto.LoginResponse;
import com.devcourse.ReviewRanger.user.dto.UpdateRequest;
import com.devcourse.ReviewRanger.user.dto.ValidateEmailRequest;
import com.devcourse.ReviewRanger.user.dto.ValidateNameRequest;

@RestController
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/sign-up")
	public RangerResponse<Void> join(@RequestBody @Valid JoinRequest request) {
		Boolean joinSuccess = userService.join(request);

		return RangerResponse.ok(joinSuccess);
	}

	@PostMapping("/login")
	public RangerResponse<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
		LoginResponse loginResponse = userService.login(request);

		return RangerResponse.ok(loginResponse);
	}

	@PostMapping("/members/logout")
	public RangerResponse<Void> logout(
		@RequestHeader("Authorization") String accessToken
	) {
		userService.logout(accessToken);
		return RangerResponse.noData();
	}

	@PostMapping("/members/check-name")
	public RangerResponse<Void> checkname(@RequestBody @Valid ValidateNameRequest request) {
		boolean notExistName = userService.isNotExistName(request.name());

		return RangerResponse.ok(notExistName);
	}

	@PostMapping("/members/check-email")
	public RangerResponse<Void> checkEmail(@RequestBody @Valid ValidateEmailRequest request) {
		boolean notExistEmail = userService.isNotExistEmail(request.email());

		return RangerResponse.ok(notExistEmail);
	}

	@PatchMapping("/members/profile")
	public RangerResponse<Void> update(
		@AuthenticationPrincipal UserPrincipal user,
		@RequestBody @Valid UpdateRequest updateRequest
	) {
		userService.updateInfo(user.getId(), updateRequest);
		return RangerResponse.noData();
	}
}
