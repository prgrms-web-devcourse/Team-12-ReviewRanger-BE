package com.devcourse.ReviewRanger.user.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.devcourse.ReviewRanger.user.dto.JoinRequest;
import com.devcourse.ReviewRanger.user.dto.ValidateEmailRequest;
import com.devcourse.ReviewRanger.user.dto.ValidateNameRequest;
import com.devcourse.ReviewRanger.user.service.UserService;

import jakarta.validation.Valid;

@RestController
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/sign-up")
	public Boolean join(@RequestBody @Valid JoinRequest request) {
		return userService.join(request);
	}

	@PostMapping("/members/check-name")
	public Boolean checkname(@RequestBody @Valid ValidateNameRequest request) {
		return !userService.checkName(request.name());
	}

	@PostMapping("/members/check-email")
	public Boolean checkEmail(@RequestBody @Valid ValidateEmailRequest request) {
		return !userService.checkEmail(request.email());
	}
}
