package com.devcourse.ReviewRanger.user.dto;

import static com.devcourse.ReviewRanger.common.regex.UserRegex.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public record ValidateNameRequest(
	@Column(nullable = false, length = 20)
	@NotBlank(message = "이름은 빈값 일 수 없습니다.")
	@Pattern(regexp = NAME_REGEXP, message = "이름 형식이 맞지 않습니다.")
	String name
) {
}
