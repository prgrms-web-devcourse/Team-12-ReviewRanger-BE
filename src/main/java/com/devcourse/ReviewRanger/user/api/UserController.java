package com.devcourse.ReviewRanger.user.api;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.devcourse.ReviewRanger.common.response.RangerResponse;
import com.devcourse.ReviewRanger.user.application.UserService;
import com.devcourse.ReviewRanger.user.domain.UserPrincipal;
import com.devcourse.ReviewRanger.user.dto.GetUserResponse;
import com.devcourse.ReviewRanger.user.dto.JoinRequest;
import com.devcourse.ReviewRanger.user.dto.LoginRequest;
import com.devcourse.ReviewRanger.user.dto.LoginResponse;
import com.devcourse.ReviewRanger.user.dto.UpdateNameRequest;
import com.devcourse.ReviewRanger.user.dto.UpdatePasswordRequest;
import com.devcourse.ReviewRanger.user.dto.UserInfoResponse;
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
		@ApiResponse(responseCode = "409", description = "이미 가입된 아이디를 입력한 경우"),
		@ApiResponse(responseCode = "409", description = "이미 가입된 이메일을 입력한 경우")
	})
	@PostMapping("/sign-up")
	public RangerResponse<Void> join(@RequestBody @Valid JoinRequest request) {
		Boolean joinSuccess = userService.join(request);

		return RangerResponse.ok(joinSuccess);
	}

	@Tag(name = "user")
	@Operation(summary = "로그인", description = "사용자가 로그인을 요청하는 API", responses = {
		@ApiResponse(responseCode = "200", description = "로그인 성공"),
		@ApiResponse(responseCode = "422", description = "잘못된 로그인 정보를 입력한 경우"),
	})
	@PostMapping("/login")
	public RangerResponse<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
		LoginResponse loginResponse = userService.login(request);

		return RangerResponse.ok(loginResponse);
	}

	@Tag(name = "user")
	@Operation(summary = "[토큰] 로그아웃", description = "[토큰] 사용자가 로그아웃을 요청하는 API", responses = {
		@ApiResponse(responseCode = "200", description = "로그아웃 성공"),
		@ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
	})
	@PostMapping("/members/logout")
	public RangerResponse<Void> logout(
		@RequestHeader("Authorization") String accessToken
	) {
		userService.logout(accessToken);
		return RangerResponse.noData();
	}

	@Tag(name = "user")
	@Operation(summary = "이름 중복검사", description = "이름 중복 검사 API", responses = {
		@ApiResponse(responseCode = "200", description = "이름이 중복되지 않음"),
		@ApiResponse(responseCode = "409", description = "이미 가입된 이름을 입력한 경우")
	})
	@PostMapping("/members/check-name")
	public RangerResponse<Void> checkname(@RequestBody @Valid ValidateNameRequest request) {
		boolean notExistName = userService.isNotExistName(request.name());

		return RangerResponse.ok(notExistName);
	}

	@Tag(name = "user")
	@Operation(summary = "이메일 중복검사", description = "이메일 중복 검사 API", responses = {
		@ApiResponse(responseCode = "200", description = "이메일이 중복되지 않음"),
		@ApiResponse(responseCode = "409", description = "이미 가입된 이메일을 입력한 경우")
	})
	@PostMapping("/members/check-email")
	public RangerResponse<Void> checkEmail(@RequestBody @Valid ValidateEmailRequest request) {
		boolean notExistEmail = userService.isNotExistEmail(request.email());

		return RangerResponse.ok(notExistEmail);
	}

	@Tag(name = "user")
	@Operation(summary = "[토큰] 사용자 이름 변경", description = "[토큰] 사용자가 이름을 변경하는 API", responses = {
		@ApiResponse(responseCode = "200", description = "이름 변경 성공"),
		@ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
		@ApiResponse(responseCode = "409", description = "이미 가입된 아이디를 입력한 경우")
	})
	@PatchMapping("/members/profile-name")
	public RangerResponse<Void> updateName(
		@AuthenticationPrincipal UserPrincipal user,
		@RequestBody @Valid UpdateNameRequest updateNameRequest
	) {
		userService.updateName(user.getId(), updateNameRequest.name());
		return RangerResponse.noData();
	}

	@Tag(name = "user")
	@Operation(summary = "[토큰] 사용자 프로필 변경", description = "[토큰] 사용자가 프로필 사진을 변경하는 API", responses = {
		@ApiResponse(responseCode = "200", description = "프로필 사진 변경 성공"),
	})
	@PutMapping("/members/profile-image")
	public RangerResponse<Void> updateImage(
		@AuthenticationPrincipal UserPrincipal user,
		@RequestParam("image") MultipartFile multipartFile
	) throws IOException {
		userService.updateImage(user.getId(), multipartFile);

		return RangerResponse.noData();
	}

	@Tag(name = "user")
	@Operation(summary = "[토큰] 사용자 비밀번호 변경", description = "[토큰] 사용자가 비밀번호를 변경하는 API", responses = {
		@ApiResponse(responseCode = "200", description = "비밀번호 변경 성공"),
		@ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
	})
	@PatchMapping("/members/profile-password")
	public RangerResponse<Void> updatePassword(
		@AuthenticationPrincipal UserPrincipal user,
		@RequestBody @Valid UpdatePasswordRequest updatePasswordRequest
	) {
		userService.updatePassword(user.getId(), updatePasswordRequest.password());
		return RangerResponse.noData();
	}

	@Tag(name = "user")
	@Operation(summary = "사용자 전체 조회", description = "모든 사용자를 조회하는 API", responses = {
		@ApiResponse(responseCode = "200", description = "모든 사용자 조회 성공"),
	})
	@GetMapping("/members")
	public RangerResponse<List<GetUserResponse>> getAllUsers() {
		List<GetUserResponse> allUsers = userService.getAllUsers();

		return RangerResponse.ok(allUsers);
	}

	@Tag(name = "user")
	@Operation(summary = "[토큰] 현재 사용자 정보 조회", description = "[토큰] 현재 사용자 정보를 조회하는 API", responses = {
		@ApiResponse(responseCode = "200", description = "사용자 정보 조회 성공"),
		@ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우"),
	})
	@GetMapping("/user")
	public RangerResponse<UserInfoResponse> getUserInfo(@AuthenticationPrincipal UserPrincipal user) {
		UserInfoResponse userInfo = userService.getUserInfo(user);

		return RangerResponse.ok(userInfo);
	}
}
