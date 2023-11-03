package com.devcourse.ReviewRanger.user.dto;

import static com.devcourse.ReviewRanger.common.regex.UserRegex.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public record UpdateRequest(
	@NotBlank(message = "이름은 빈값 일 수 없습니다.")
	@Pattern(regexp = NAME_REGEXP, message = "이름 형식이 맞지 않습니다.")
	String name,
	
	@NotBlank(message = "비밀번호는 빈값 일 수 없습니다.")
	@Pattern(regexp = PASSWORD_REGEXP, message = "비밀번호 형식이 맞지 않습니다.")
	String password
) {
}
