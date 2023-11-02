package com.devcourse.ReviewRanger.user.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.devcourse.ReviewRanger.common.response.RangerResponse;
import com.devcourse.ReviewRanger.user.dto.JoinRequest;
import com.devcourse.ReviewRanger.user.dto.LoginRequest;
import com.devcourse.ReviewRanger.user.dto.LoginResponse;
import com.devcourse.ReviewRanger.user.dto.ValidateEmailRequest;
import com.devcourse.ReviewRanger.user.dto.ValidateNameRequest;
import com.devcourse.ReviewRanger.user.service.UserService;

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
	public RangerResponse<LoginResponse> login(@RequestBody LoginRequest request) {
		LoginResponse loginResponse = userService.login(request);

		return RangerResponse.ok(loginResponse);
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
}
