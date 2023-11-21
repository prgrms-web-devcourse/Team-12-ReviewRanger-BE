package com.devcourse.ReviewRanger.user.api;

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

import com.devcourse.ReviewRanger.auth.domain.UserPrincipal;
import com.devcourse.ReviewRanger.common.response.RangerResponse;
import com.devcourse.ReviewRanger.user.application.UserService;
import com.devcourse.ReviewRanger.user.dto.GetUserResponse;
import com.devcourse.ReviewRanger.user.dto.UpdateNameRequest;
import com.devcourse.ReviewRanger.user.dto.UpdatePasswordRequest;
import com.devcourse.ReviewRanger.user.dto.UserInfoResponse;

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
	) {
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
