package com.devcourse.ReviewRanger.user.dto;

import static com.devcourse.ReviewRanger.common.regex.UserRegex.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public record LoginRequest(
	@NotBlank(message = "이메일을 입력해주세요.")
	@Pattern(regexp = EMAIL_REGEXP, message = "이메일 형식이 맞지 않습니다.")
	String email,

	@NotBlank(message = "비밀번호를 입력해주세요.")
	String password
) {
}
