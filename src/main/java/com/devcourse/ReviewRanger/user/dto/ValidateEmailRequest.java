package com.devcourse.ReviewRanger.user.dto;

import static com.devcourse.ReviewRanger.common.regex.UserRegex.*;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ValidateEmailRequest(
	@Column(nullable = false, length = 30)
	@NotBlank(message = "이메일은 빈값 일 수 없습니다.")
	@Pattern(regexp = EMAIL_REGEXP, message = "이메일 형식이 맞지 않습니다.")
	String email
) {
}
