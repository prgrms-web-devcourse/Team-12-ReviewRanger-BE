package com.devcourse.ReviewRanger.auth.api;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.devcourse.ReviewRanger.auth.application.AuthService;
import com.devcourse.ReviewRanger.common.response.RangerResponse;
import com.devcourse.ReviewRanger.auth.dto.JoinRequest;
import com.devcourse.ReviewRanger.auth.dto.LoginRequest;
import com.devcourse.ReviewRanger.auth.dto.LoginResponse;
import com.devcourse.ReviewRanger.auth.dto.ValidateEmailRequest;
import com.devcourse.ReviewRanger.auth.dto.ValidateNameRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "auth", description = "사용자 인증 API")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@Tag(name = "auth")
	@Operation(summary = "회원가입", description = "사용자가 회원가입을 요청하는 API", responses = {
		@ApiResponse(responseCode = "200", description = "회원가입 성공"),
		@ApiResponse(responseCode = "409", description = "이미 가입된 아이디를 입력한 경우"),
		@ApiResponse(responseCode = "409", description = "이미 가입된 이메일을 입력한 경우")
	})
	@PostMapping("/sign-up")
	public RangerResponse<Void> join(@RequestBody @Valid JoinRequest request) {
		Boolean joinSuccess = authService.join(request);

		return RangerResponse.ok(joinSuccess);
	}

	@Tag(name = "auth")
	@Operation(summary = "로그인", description = "사용자가 로그인을 요청하는 API", responses = {
		@ApiResponse(responseCode = "200", description = "로그인 성공"),
		@ApiResponse(responseCode = "422", description = "잘못된 로그인 정보를 입력한 경우"),
	})
	@PostMapping("/login")
	public RangerResponse<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
		LoginResponse loginResponse = authService.login(request);

		return RangerResponse.ok(loginResponse);
	}

	@Tag(name = "auth")
	@Operation(summary = "[토큰] 로그아웃", description = "[토큰] 사용자가 로그아웃을 요청하는 API", responses = {
		@ApiResponse(responseCode = "200", description = "로그아웃 성공"),
		@ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
	})
	@PostMapping("/members/logout")
	public RangerResponse<Void> logout(
		@RequestHeader("Authorization") String accessToken
	) {
		authService.logout(accessToken);
		return RangerResponse.noData();
	}

	@Tag(name = "auth")
	@Operation(summary = "이름 중복검사", description = "이름 중복 검사 API", responses = {
		@ApiResponse(responseCode = "200", description = "이름이 중복되지 않음"),
		@ApiResponse(responseCode = "409", description = "이미 가입된 이름을 입력한 경우")
	})
	@PostMapping("/members/check-name")
	public RangerResponse<Void> checkname(@RequestBody @Valid ValidateNameRequest request) {
		boolean notExistName = authService.isNotExistName(request.name());

		return RangerResponse.ok(notExistName);
	}

	@Tag(name = "auth")
	@Operation(summary = "이메일 중복검사", description = "이메일 중복 검사 API", responses = {
		@ApiResponse(responseCode = "200", description = "이메일이 중복되지 않음"),
		@ApiResponse(responseCode = "409", description = "이미 가입된 이메일을 입력한 경우")
	})
	@PostMapping("/members/check-email")
	public RangerResponse<Void> checkEmail(@RequestBody @Valid ValidateEmailRequest request) {
		boolean notExistEmail = authService.isNotExistEmail(request.email());

		return RangerResponse.ok(notExistEmail);
	}
}
