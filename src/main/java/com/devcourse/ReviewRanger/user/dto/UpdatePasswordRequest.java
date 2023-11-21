package com.devcourse.ReviewRanger.user.dto;

import static com.devcourse.ReviewRanger.common.regex.UserRegex.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 비밀번호 변경 요청 DTO")
public record UpdatePasswordRequest(
	@Schema(description = "변경 할 비밀번호")
	@NotBlank(message = "비밀번호는 빈값 일 수 없습니다.")
	@Pattern(regexp = PASSWORD_REGEXP, message = "비밀번호 형식이 맞지 않습니다.")
	String password
) {
}
